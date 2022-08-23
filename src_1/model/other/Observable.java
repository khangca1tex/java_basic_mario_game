package model.other;

import java.util.ArrayList;
import java.util.Queue;

import controller.ControllerGame;

public class Observable implements IObserable {

	public ControllerGame map;
	

	public Queue<String> needExecute;
	public ArrayList<IObserve> listObserve;
	

	public Observable(ControllerGame map) {
		this.map = map;
		listObserve = new ArrayList<>();

		needExecute = map.listExe;


	}
	@Override
	public void attach(IObserve a) {
		listObserve.add(a);

	}

	@Override
	public void dettach(IObserve d) {
		listObserve.remove(d);

	}
	
	public void setSource(ControllerGame map){
		this.map=map;
	}

	@Override
	public void notifier() {

		while (!needExecute.isEmpty()) {

			String s = needExecute.poll();

			for (IObserve iObserve : listObserve) {
				iObserve.updateObserve(s);
			}
			
		}
	}

}
