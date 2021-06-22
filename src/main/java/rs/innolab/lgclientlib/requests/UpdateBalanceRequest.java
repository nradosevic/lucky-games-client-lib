package rs.innolab.lgclientlib.requests;

public class UpdateBalanceRequest {
	
	private long winnings;
	private long totalBet;
	
	public long getWinnings() {
		return winnings;
	}
	public void setWinnnings(long winnings) {
		this.winnings = winnings;
	}
	public long getTotalBet() {
		return totalBet;
	}
	public void setTotalBet(long totalBet) {
		this.totalBet = totalBet;
	}
	
	

}
