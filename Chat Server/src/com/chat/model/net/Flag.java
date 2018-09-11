package com.chat.model.net;

public enum Flag {
	MSG(1),EXIT(0),ERROR(-1),BROADCAST(2);
	
	public int id;
	Flag(int id) {
		this.id  = id;
	}
	
	public static Flag getValue(int value) {
		for(Flag f : Flag.values()) {
			if(f.id == value)
				return f;
		}
		return Flag.ERROR;
	}
}
