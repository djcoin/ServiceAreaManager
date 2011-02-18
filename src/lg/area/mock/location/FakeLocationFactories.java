package lg.area.mock.location;

public class FakeLocationFactories {

	public interface ILocationFactory {
		ILocationSource createLocationSource(String mockLocationProvider, Boolean loop);
	}
	
	public static class FakeCustomLocationFactory implements ILocationFactory{

		private double[] data = new double[0];

		@Override
		public ILocationSource createLocationSource(
				String mockLocationProvider, Boolean loop) {			
			return new FakeCustomizeLocationSource(mockLocationProvider, loop, this.data);
		}		
		
		public void setData(double[] d){
			this.data = d;
		}
		
	}
	
	public static class FakeLocationFactory implements ILocationFactory {
		@Override
		public ILocationSource createLocationSource(String mockLocationProvider, Boolean loop) {			
			return new FakeLocationSource(mockLocationProvider, loop);
		}
	}	
}
