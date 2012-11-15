package org.sikuli.api.remote.client;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Map;

import org.sikuli.api.Screen;
import org.sikuli.api.remote.Remote;
import org.sikuli.api.robot.desktop.DesktopScreen;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;

public class RemoteScreen implements Screen {

	final private Remote remote;
	final private int width;
	final private int height;

	public RemoteScreen(Remote remoteRef){
		remote = remoteRef;		
		Dimension d = getSize();
		width = d.width;
		height = d.height;		
	}

	@Override
	public BufferedImage getScreenshot(int x, int y, int width, int height) {		
		GetScreenshot method = new GetScreenshot();
		return method.call(remote, ImmutableMap.of("x",x,"y",y,"width",width,"height",height));
	}

	@Override
	public Dimension getSize() {
		GetSize action = new GetSize();
		return action.call(remote, null);
	}

	@Override
	public String toString(){
		return Objects.toStringHelper(this).add("width",width).add("height",height).toString();
	}

	static public class GetSize extends AbstractRemoteMethod<Dimension>{

		@Override
		public String getName(){
			return GET_SIZE;
		}

		@Override
		protected Dimension execute(){
			Screen s = new DesktopScreen(0);
			return s.getSize();
		}

		@Override
		protected Map<String,?> encodeResult(Dimension size){			
			return ImmutableMap.of("width",size.width,"height",size.height);
		}

		@Override
		protected Dimension decodeResult(Remote remote, Map<String,?> valueAsMap){
			int width = ((Long) valueAsMap.get("width")).intValue();
			int height = ((Long) valueAsMap.get("height")).intValue();
			return new Dimension(width,height);
		}

	}


	static public class GetScreenshot extends AbstractRemoteMethod<BufferedImage>{	

		private int x;
		private int y;
		private int width;
		private int height;

		@Override
		public String getName(){
			return GET_SCREENSHOT;
		}

		@Override
		protected BufferedImage execute(){
			Screen screen = new DesktopScreen(0);
			BufferedImage image = screen.getScreenshot(x, y, width, height);
			return image;
		}

		@Override
		protected Map<String,?> encodeResult(BufferedImage image){
			return ImmutableMap.of("image", ConverterUtil.encodeImage(image));
		}

		@Override
		protected BufferedImage decodeResult(Remote remote, Map<String,?> value){
			String imageString = ((String) value.get("image"));
			return ConverterUtil.decodeImage(imageString);
		}

		@Override
		public void readParameters(Map<String, ?> allParameters){				
			x = ((Long) allParameters.get("x")).intValue();
			y = ((Long) allParameters.get("y")).intValue();
			width = ((Long) allParameters.get("width")).intValue();
			height = ((Long) allParameters.get("height")).intValue();
		}
		
	}
}
