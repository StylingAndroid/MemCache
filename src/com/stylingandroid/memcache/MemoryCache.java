package com.stylingandroid.memcache;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;

public class MemoryCache
{
	private final Map<String, WeakReference<Bitmap>> cache = new HashMap<String, WeakReference<Bitmap>>();
	private final Context context;
	
	/*
	 * IMPORTANT NOTE:
	 * This code is designed to demonstrate the concept of a memory
	 * cache and has deliberately been simplified to keep the technique
	 * clear. However, this will most likely cause ConcurrentModificatioExceptions
	 * to be thrown unless it is made thread safe.
	 */

	public MemoryCache( Context context )
	{
		/*
		 * We may have been given an Application context, but just in case let's
		 * request one to ensure that we don't associate our Bitmaps with an
		 * Activity Context which could result in a context leak.
		 */
		this.context = context.getApplicationContext();
	}

	@Override
	public String toString()
	{
		return "Cache size: " + cache.size();
	}

	public Bitmap getImage( String assetName )
	{
		WeakReference<Bitmap> ref = cache.get( assetName );
		Bitmap bitmap = ref == null ? null : ref.get();
		if (bitmap == null)
		{
			bitmap = Utils.loadAsset( context, assetName );
			if (bitmap != null)
			{
				cache.put( assetName, new WeakReference<Bitmap>( bitmap ) );
			}
		}
		return bitmap;
	}

	public boolean isCached( String assetName )
	{
		refreshCache();
		return cache.containsKey( assetName );
	}

	public void refreshCache()
	{
		List<String> removals = new LinkedList<String>();
		for (String key : cache.keySet())
		{
			WeakReference<Bitmap> bm = cache.get( key );
			if (bm.get() == null)
			{
				removals.add( key );
			}
		}
		for (String key : removals)
		{
			cache.remove( key );
		}
	}
}
