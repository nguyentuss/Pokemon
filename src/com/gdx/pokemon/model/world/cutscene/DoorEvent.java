package com.gdx.pokemon.model.world.cutscene;

import com.gdx.pokemon.model.world.Door;
import com.gdx.pokemon.model.world.Door.STATE;

public class DoorEvent extends CutsceneEvent {
	
	private boolean opening;
	private Door door;
	
	private boolean finished = false;
	
	public DoorEvent(Door door, boolean opening) {
		this.door = door;
		this.opening = opening;
		
	}
	
	@Override
	public void begin(CutscenePlayer player) {
		super.begin(player);
		if (door.getState() == STATE.OPEN && opening == false) {
			door.close();
		} else if (door.getState() == STATE.CLOSED && opening == true) {
			door.open();
		}
	}

	@Override
	public void update(float delta) {
		if (opening == true && door.getState() == STATE.OPEN) {
			finished = true;
		} else if (opening == false && door.getState() == STATE.CLOSED) {
			finished = true;
		}
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
	public void screenShow() {}

}
