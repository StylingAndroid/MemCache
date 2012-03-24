package com.stylingandroid.memcache;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.TimingLogger;
import android.widget.ImageView;

public class MemCacheActivity extends Activity {
	public static final String TAG = "MemoryCache";
	public static final String ASSET_NAME = "sa.png";

	private ImageView imageView1 = null;
	private ImageView imageView2 = null;

	private MemoryCache memCache = null;

	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.main );

		imageView1 = (ImageView) findViewById( R.id.imageView1 );
		imageView2 = (ImageView) findViewById( R.id.imageView2 );
		memCache = new MemoryCache( getApplicationContext() );

		loadManual();
		loadManual();
		Log.d( TAG, memCache.toString() );
		loadCached();
		Log.d( TAG, memCache.toString() );
		loadManual();
		Log.d( TAG, "Cached: " + memCache.isCached( ASSET_NAME ) );
		Log.d( TAG, memCache.toString() );
		System.gc();
		Log.d( TAG, "GC" );
		Log.d( TAG, "Cached: " + memCache.isCached( ASSET_NAME ) );
		Log.d( TAG, memCache.toString() );
	}

	private void loadManual()
	{
		TimingLogger tl = new TimingLogger( TAG, "Standard image loading" );
		imageView1.setImageBitmap( Utils.loadAsset( this, ASSET_NAME ) );
		tl.addSplit( "first" );
		imageView2.setImageBitmap( Utils.loadAsset( this, ASSET_NAME ) );
		tl.addSplit( "second" );
		tl.dumpToLog();
	}
	
	private void loadCached()
	{
		TimingLogger tl = new TimingLogger( TAG, "Cached image loading" );
		imageView1.setImageBitmap( memCache.getImage( ASSET_NAME ) );
		tl.addSplit( "first" );
		imageView2.setImageBitmap( memCache.getImage( ASSET_NAME ) );
		tl.addSplit( "second" );
		tl.dumpToLog();
	}
}