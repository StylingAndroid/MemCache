package com.stylingandroid.memcache;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class LruMemoryCache extends LruCache<String, Bitmap>
{
	private final Context context;
	
	public LruMemoryCache(Context context)
	{
		super( 10 );
		this.context = context;
	}

	@Override
	protected Bitmap create( String key )
	{
		return Utils.loadAsset( context, key );
	}
}
