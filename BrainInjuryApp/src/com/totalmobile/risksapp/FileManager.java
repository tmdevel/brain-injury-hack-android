package com.totalmobile.risksapp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class FileManager {
	
	String string;
	
	
	public FileManager(String title) {
		title = string;
	}

	
	@SuppressWarnings("deprecation")
	public static Uri CopyReadAssets(String s, Context c){
		
		AssetManager assetManager = c.getAssets();
		
		InputStream in = null;
		OutputStream out = null;
		
		String pathName = s + ".pdf";
		
		File file = new File(c.getFilesDir(), pathName);
		
		try
		{
			in = assetManager.open(pathName);
			out = c.openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);
			
			copyFile(in, out);
			in.close();in = null;
			out.flush();
			out.close();out = null;
		} 
		catch(Exception e){
			Log.e("tag", e.getMessage());
		}
		
	    Uri fileUri = Uri.parse("file://" + c.getFilesDir() + "/" + pathName);
	    return fileUri;
	}

	private static void copyFile(InputStream in, OutputStream out) throws IOException {
		
		byte [] buffer = new byte[1024];
		int read;
		while((read = in.read(buffer)) != -1){
			out.write(buffer, 0, read);
		}
		
	}
	
	
	
		
}
