package com.qc.magic.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.FileCopyUtils;

public class FileUtils {
	
	public static final String DEST_DIR="D:\\filedir\\";
	
	public static void removeAllFiles(String src, String dest) {
		File srcFile = new File(src);
		List<File> files = getFiles(srcFile);
		for (int i=0; i<files.size(); i++) {
			try {
				File f = files.get(i);
				//String extName = f.getName().substring(f.getName().lastIndexOf("."), f.getName().length());
				FileCopyUtils.copy(f, new File(dest+"/"+i+".jpg"/*extName*/));
			} catch (IOException e) {
				System.out.println("文件："+files.get(i).getAbsolutePath()+" 移动出现异常");
			}
		}
	}
	
	public static List<File> getFiles(File srcFile) {
		List<File> list = new ArrayList<>();
		File[] listFiles = srcFile.listFiles();
		for (File f : listFiles) {
			if (f.isFile()) {
				list.add(f);
			} else if (f.isDirectory()) {
				list.addAll(getFiles(f));
			}
		}
		return list;
	}

	public static void main(String[] args) {
		removeAllFiles("D:\\图片", "D:\\filedir");
	}

}
