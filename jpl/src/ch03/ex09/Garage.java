package ch03.ex09;

// 何個かのVehicleオブジェクトを配列に保存できるGarageクラスを作成しなさい。
// cloneをテストするGarage.mainメソッドを書きなさい。

class Garage implements Cloneable{
	private final int CAPACITY;
	private Vehicle[] vehicles;
	public Garage(int capacity) {
		CAPACITY = capacity;
		vehicles = new Vehicle[CAPACITY];
	}
	public Garage(Vehicle[] vehicles) {
		CAPACITY = vehicles.length;
		this.vehicles = vehicles;
	}
	public Vehicle[] getVehicles() {
		return vehicles;
	}
	/**
	 * 1台ガレージに入れる
	 * @param vehicle
	 */
	public boolean setVehicle(int idx,Vehicle vehicle) {
		if (vehicles[idx] == null) return false;
		vehicles[idx] = vehicle;
		return true;
	}
	/**
	 * 複数台ガレージに入れる
	 * @param vehicles
	 */
	public void setVehicle(Vehicle[] vehicles) {
		int idx = 0,length = this.vehicles.length;
		for (int i = 0;i < length;i++) {
			if (setVehicle(i, vehicles[idx])) idx++;
		}
	}

	@Override
	public Garage clone() {
		Garage cloneGarage;
		try {
			cloneGarage = (Garage)(super.clone());
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
		// 参照を変える
		cloneGarage.vehicles = vehicles.clone();
		for (int i = 0;i < vehicles.length;i++) {
			// 値をコピーする
			cloneGarage.vehicles[i] = vehicles[i].clone();
		}
		return cloneGarage;
	}

	public static void main(String[] args) {
		Vehicle vehicles[] = {
				new Vehicle("Alice", 25, 0),
				new Vehicle("Bob", 25, 0),
				new Vehicle("Charley", 25, 0)
		};
		Garage g = new Garage(vehicles);
		Garage g2 = g.clone();
		for (int i = 0;i < vehicles.length;i++) {
			System.out.println(g.getVehicles()[i]);
			System.out.println(g2.getVehicles()[i]);
		}
	}
}