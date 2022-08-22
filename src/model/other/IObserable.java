package model.other;

public interface IObserable {
	
	public void attach(IObserve a);
	public void dettach(IObserve d);
	public void notifier();
}
