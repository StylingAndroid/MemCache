package com.stylingandroid.memcache;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

public class LruMemoryCache extends LruCache<String, Bitmap>
{
	private final Context context;
	
	public LruMemoryCache(Context context)
	{
		super( 5 * 1024 * 1024 );
		this.context = context;
	}

	@Override
	protected Bitmap create( String key )
	{
		return Utils.loadAsset( context, key );
	}
	
	@Override
	protected int sizeOf( String key, Bitmap value )
	{
		return value.getByteCount();
	}
}
