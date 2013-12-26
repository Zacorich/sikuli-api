package org.sikuli.api.visual;

import org.sikuli.api.visual.element.BoxElement;
import org.sikuli.api.visual.element.CircleElement;
import org.sikuli.api.visual.element.DotElement;
import org.sikuli.api.visual.element.Element;

public class CanvasBuilder {

	private Canvas canvas;

	public CanvasBuilder(Canvas canvas) {
		this.canvas = canvas;
	}
	
	public class ElementAdder {
		public ElementAreaSetter box() {
			BoxElement newElement = new BoxElement();				
			addElement(newElement);
			return new ElementAreaSetter(newElement);
		}
		public ElementAreaSetter circle() {
			CircleElement newElement = new CircleElement();				
			addElement(newElement);
			return new ElementAreaSetter(newElement);
		}
		public ElementPointSetter dot() {
			DotElement newElement = new DotElement();				
			addElement(newElement);
			return new ElementPointSetter(newElement);
		}
	}
	
	public class ElementPointSetter {
		private Element element;		
		public ElementPointSetter(Element element) {
			this.element = element;
		}
		
		public Element at(int x, int y){
			element.x = x;
			element.y = y;
			return element;
		}
		
		public Element centeredAt(int x, int y){			
			element.x = x - element.width/2;
			element.y = y - element.height/2;
			return element;
		}
	}
	
	public class ElementAreaSetter {
		private Element element;

		public ElementAreaSetter(Element element){
			this.element = element;
		}
		
		public ElementPointSetter size(int width, int height){
			element.width = width;
			element.height = height;
			return new ElementPointSetter(element);
		}
		
		public Element around(int x, int y, int width, int height){
			element.x = x;
			element.y = y;
			element.width = width;
			element.height = height;
			return element;
		}
	}
	
	private Element addElement(Element element){
		canvas.add(element);
		return element;
	}
	
}

