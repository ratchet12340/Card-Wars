package com.paragongames.tcgproject.util;

public enum WarningType
{
	DECK_EMPTY("DECK IS EMPTY"),
	HAND_FULL("HAND IS TOO FULL"),
	INSUFFICIENT_RESOURCES("INSUFFICIENT RESOURCES"),
	INVALID_TILE_PLACEMENT("CANNOT PLACE CARD THERE"),
	INVALID_MOVE("CANNOT MOVE THERE"),
	UNSUPPORTED_OPERATION("UNSUPPORTED OPERATION"),
	PLAYER_TURN_BEGIN("YOUR TURN"),
	PLAYER_TURN_END("TURN OVER"),
	DRAW_LIMIT("CAN ONLY DRAW ONCE PER TURN");
	
	private String msg;
	
	WarningType(String msg)
	{
		this.msg = msg;
	}

	public String getMessage()
	{
		return msg;
	}
}
