package com.stylingandroid.memcache;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

final class Utils
{
	private static final String TAG = MemCacheActivity.TAG;
	
	public static Bitmap loadAsset( Context context, String assetName )
	{
		Bitmap bitmap = null;
		InputStream is = null;
		try
		{
			is = context.getAssets().open( assetName );
			bitmap = BitmapFactory.decodeStream( is );
		} catch (Exception e)
		{
			Log.e( TAG, "Load Error", e );
		} finally
		{
			if (is != null)
			{
				try
				{
					is.close();
				} catch (Exception e)
				{
				}
			}
		}
		return bitmap;
	}
}
