package com.whpu.house.service;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.io.FileUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whpu.house.mapper.HouseMapper;
import com.whpu.house.pojo.House;
import com.whpu.house.pojo.HouseAddr;
import com.whpu.house.pojo.HousePic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class HouseService {

	private HouseMapper houseMapper;

//	@Autowired
//	private NewHouseMapper newHouseMapper;

	@Autowired
	public HouseService(HouseMapper houseMapper) {
		this.houseMapper = houseMapper;
	}

	public List<HouseAddr> getAddrList() {
		return houseMapper.selectAddr();
	}

	public int addAddrConfig(HouseAddr houseAddr) {
		return houseMapper.insertAddr(houseAddr);
	}

	public List<String> addHousePic(MultipartFile[] files, HttpServletRequest request, Integer house_id, Integer status)
			throws IOException {
		LinkedList<String> list = new LinkedList<>();
		 String localString = "http://"+request.getServerName() +":"+ request.getServerPort();
		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue;
			}
			// 构建上传文件的存放路径
			String path = request.getServletContext().getRealPath("/upload/");
			// 获取上传的文件名称，并结合存放路径，构建新的文件名称
			String filename = file.getOriginalFilename();
			File filepath = new File(path, filename);

			// 判断路径是否存在，不存在则新创建一个
			if (!filepath.getParentFile().exists()) {
				filepath.getParentFile().mkdirs();
			}
			// 将上传文件保存到目标文件目录
			String uuid = UUID.randomUUID().toString();
			String s = path + uuid + filename;
			String s1 = localString + "/upload/" + uuid + filename;
			file.transferTo(new File(s));
			HousePic housePic = new HousePic();
			housePic.setHouse_id(house_id);
			housePic.setUrl(s1);
			housePic.setStatus(status);
			houseMapper.insertHousePic(housePic);
			list.add(s);
		}
		return list;
	}

	@Transactional
	public int addHouse(HttpServletRequest request, House house, MultipartFile[] files) throws IOException {
		int i = houseMapper.insertHouse(house);
		if (files == null) {
			return i;
		}
		saveHousePicToBase64(files, request, house.getId(), 1);
		return i;
	}

	public int updateHouse(House house) {
		return houseMapper.updateHouse(house);
	}

	public int updateHousePic(HousePic housePic) {
		return houseMapper.updateHousePic(housePic);
	}

	public PageInfo<Map> getHouseList(House house, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Map> houses = houseMapper.selectHouseList(house);
		houses.forEach(house1 -> {
			List<HousePic> housePics = houseMapper.selectHousePic((Long) house1.get("id"));
			house1.put("pics", housePics);
		});
		return new PageInfo<>(houses);
	}

	public int delHouse(Integer id){
		return houseMapper.deleteHouseById(id);
	}

	public int saveHousePicToBase64(MultipartFile[] files, HttpServletRequest request, Integer house_id, Integer status) throws IOException {
		int result = 0;
		for (MultipartFile file : files) {
			// 传给前台file类型
			String suffix = FileUtil.getSuffix(file.getOriginalFilename());
			String src = "data:image/"+suffix+";base64,";
			StringBuffer sb = new StringBuffer(src);
			String encode = Base64Encoder.encode(file.getBytes());
			String imgUrl = (sb.append(encode)).toString();
			HousePic housePic = new HousePic();
			housePic.setHouse_id(house_id);
			housePic.setUrl(imgUrl);
			housePic.setStatus(status);
			int i = houseMapper.insertHousePic(housePic);
			result += i;
		}
		return result;
	}

	public int updateHouseById(Integer id , String status){
		return houseMapper.updateHouseById(id , status);
	}
}
