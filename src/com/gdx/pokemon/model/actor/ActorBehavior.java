package com.gdx.pokemon.model.actor;


public abstract class ActorBehavior {
	
	private Actor actor;
	
	public ActorBehavior(Actor actor) {
		this.actor = actor;
	}
	
	/**
	 * Updates the state of the behavior
	 * @param delta	Seconds since last update
	 */
	public abstract void update(float delta);
	
	protected Actor getActor() {
		return actor;
	}

}
