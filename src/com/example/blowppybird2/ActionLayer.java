package com.example.blowppybird2;

import org.cocos2d.actions.UpdateCallback;
import org.cocos2d.layers.CCLayer;

import ca.uol.aig.fftpack.RealDoubleFFT;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;

//第一个场景
/**
 * @author Administrator
 *
 */
public class ActionLayer extends CCLayer {

	private int frequency = 8000;
    private int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    private int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
    private RealDoubleFFT transformer;
    private int blockSize = 256;
	bird birdSpriteBird;
	public static boolean RunMark;
	RecordAudio recordAudio;
	
	RulerSprite rulerSprite;
	public ActionLayer()
	{
		this.birdSpriteBird=new bird();
		this.addChild(this.birdSpriteBird.GetSprite());
		this.setIsTouchEnabled(true);
		this.RunMark=true;
		
		
		this.rulerSprite=new RulerSprite();
		this.addChild(this.rulerSprite.GetRulerSprite());
		
		this.InitRecord();
	
	}
	
	private void InitRecord()
	{
		this.transformer= new RealDoubleFFT(blockSize);
		this.recordAudio=new RecordAudio();
		RunMark=true;
		recordAudio.execute();
	}
	
	private class RecordAudio extends AsyncTask<Void, double[], Void>
	{

		long timeNow;
		double allMark=0;
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			
			int bufferSize = AudioRecord.getMinBufferSize(frequency,
                    channelConfiguration, audioEncoding);
			 AudioRecord audioRecord = new AudioRecord(
                    MediaRecorder.AudioSource.MIC, frequency,
                    channelConfiguration, audioEncoding, bufferSize);
			 short[] buffer = new short[blockSize];
			 double[] toTransform = new double[blockSize];
			 timeNow=System.currentTimeMillis();
			 try {
				 audioRecord.startRecording();
			} catch (Exception e) {
				// TODO: handle exception
				int kk=0;
				kk++;
			}
			
			 while (RunMark) {
				 //将record的数据 读到buffer中，但是我认为叫做write可能会比较合适些。
				 int bufferResult = audioRecord.read(buffer, 0, blockSize);

				 for (int i = 0; i < bufferResult; i++) {
                     	toTransform[i] = (double) buffer[i] / Short.MAX_VALUE;
				 }
				 try
                 {
                 	transformer.ft(toTransform);
                 	publishProgress(toTransform);
                 }
                 catch(Exception ee)
                 {
                 	int kk=0;
                 	kk++;
                 }
			 }
			 audioRecord.stop();
			 audioRecord.release();
			
			
			return null;
		}

		@Override
		protected void onProgressUpdate(double[]... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			
			 double all=0;
			
            for(int i=0;i<values[0].length/2;i++)
            {
            	int downy=(int)values[0][i]*10;
            	if(downy>0)
            	{
            		all+=downy;
            	}
            	else
            	{
            		all-=downy;
            	}
            }
            all=all/(values[0].length/2);
			
            if(all*100>800)
            {
            	if(System.currentTimeMillis()-this.timeNow>100&&all*100>allMark)
            	{
            		this.timeNow=System.currentTimeMillis();
            		
            		birdSpriteBird.Jump();
            		Log.v("FFTValue：", all*100+","+allMark*100);
            	}
            }
            allMark=all*100;
            rulerSprite.SetPosition(200, (float) (30+10*all));
		}				
		
		
	}
	
	
	
	
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.ccTouchesBegan(event);
	}
	@Override
	public boolean ccTouchesCancelled(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.ccTouchesCancelled(event);
	}
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		// TODO Auto-generated method stub
		this.birdSpriteBird.Jump();
		return super.ccTouchesEnded(event);
	}


	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		super.onExit();
		RunMark=false;
	}
	
	
	
}
