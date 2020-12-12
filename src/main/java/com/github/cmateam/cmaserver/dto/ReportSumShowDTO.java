package com.github.cmateam.cmaserver.dto;

public class ReportSumShowDTO {
	private Long totalAmountMedicineSale;
	private Long totalAmountService;
	private Long totalAmountReceipt;
	private Long totalAmountDebtPayment;
	private Long totalAmountDebtReceivable;
	private Long totalReceived;
	private Long totalPaymented;

	public Long getTotalAmountMedicineSale() {
		return totalAmountMedicineSale;
	}

	public void setTotalAmountMedicineSale(Long totalAmountMedicineSale) {
		this.totalAmountMedicineSale = totalAmountMedicineSale;
	}

	public Long getTotalAmountService() {
		return totalAmountService;
	}

	public void setTotalAmountService(Long totalAmountService) {
		this.totalAmountService = totalAmountService;
	}

	public Long getTotalAmountReceipt() {
		return totalAmountReceipt;
	}

	public void setTotalAmountReceipt(Long totalAmountReceipt) {
		this.totalAmountReceipt = totalAmountReceipt;
	}

	public Long getTotalAmountDebtPayment() {
		return totalAmountDebtPayment;
	}

	public void setTotalAmountDebtPayment(Long totalAmountDebtPayment) {
		this.totalAmountDebtPayment = totalAmountDebtPayment;
	}

	public Long getTotalAmountDebtReceivable() {
		return totalAmountDebtReceivable;
	}

	public void setTotalAmountDebtReceivable(Long totalAmountDebtReceivable) {
		this.totalAmountDebtReceivable = totalAmountDebtReceivable;
	}

	public Long getTotalReceived() {
		return totalReceived;
	}

	public void setTotalReceived(Long totalReceived) {
		this.totalReceived = totalReceived;
	}

	public Long getTotalPaymented() {
		return totalPaymented;
	}

	public void setTotalPaymented(Long totalPaymented) {
		this.totalPaymented = totalPaymented;
	}

}
