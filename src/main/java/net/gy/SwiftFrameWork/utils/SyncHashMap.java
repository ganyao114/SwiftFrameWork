package net.gy.SwiftFrameWork.utils;

import java.util.HashMap;

public class SyncHashMap extends HashMap{

	@Override
	public Object get(Object key) {
		// TODO Auto-generated method stub
		synchronized (this) {
			return super.get(key);
		}
	}

	@Override
	public synchronized Object put(Object key, Object value) {
		// TODO Auto-generated method stub
		synchronized (this) {
			return super.put(key, value);
		}
		
	}
	
}
