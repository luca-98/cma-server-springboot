package com.github.cmateam.cmaserver.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.cmateam.cmaserver.dto.ReportSumShowDTO;
import com.github.cmateam.cmaserver.dto.ReportSumShowListDTO;
import com.github.cmateam.cmaserver.repository.DebtPaymentSlipRepository;
import com.github.cmateam.cmaserver.repository.InvoiceDetailedRepository;
import com.github.cmateam.cmaserver.repository.PaymentVoucherRepository;
import com.github.cmateam.cmaserver.repository.ReceiptRepository;
import com.github.cmateam.cmaserver.repository.ReceiptVoucherRepository;

@Service
public class ReportSumServiceImpl {
	private ReceiptRepository receiptRepository;
	private InvoiceDetailedRepository invoiceDetailedRepository;
	private DebtPaymentSlipRepository debtPaymentSlipRepository;
	private PaymentVoucherRepository paymentVoucherRepository;
	private ReceiptVoucherRepository receiptVoucherRepository;

	@Autowired
	public ReportSumServiceImpl(ReceiptRepository receiptRepository,
			InvoiceDetailedRepository invoiceDetailedRepository, DebtPaymentSlipRepository debtPaymentSlipRepository, PaymentVoucherRepository paymentVoucherRepository,ReceiptVoucherRepository receiptVoucherRepository) {
		this.receiptRepository = receiptRepository;
		this.invoiceDetailedRepository = invoiceDetailedRepository;
		this.debtPaymentSlipRepository = debtPaymentSlipRepository;
		this.receiptVoucherRepository = receiptVoucherRepository;
		this.paymentVoucherRepository = paymentVoucherRepository;
	}

	public List<Date> getDaysBetweenDates(Date startDate, Date endDate) {
		ArrayList<Date> dates = new ArrayList<Date>();
		ArrayList<Date> dateFormats = new ArrayList<Date>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(startDate);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(endDate);

		while (cal1.before(cal2) || cal1.equals(cal2)) {
			dates.add(cal1.getTime());
			cal1.add(Calendar.DATE, 1);
		}
		for (int i = 0; i < dates.size(); i++) {
			Date date = null;
			try {
				date = formatter.parse(formatter.format(dates.get(i)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			dateFormats.add(date);
		}
		return dateFormats;
	}

	public List<ReportSumShowListDTO> showReportAccordingDate(Date startDate, Date endDate) {
		List<ReportSumShowListDTO> lstReportSumShowListDTOs = new ArrayList<>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Long totalAmountMedicineSale = (long) 0;
		Long totalAmountService = (long) 0;
		Long totalAmountReceipt = (long) 0;
		Long totalAmountDebtPayment = (long) 0;
		Long totalAmountDebtReceivable = (long) 0;
		Long totalAmountReceiptVoucher = (long) 0;
		Long totalAmountPaymentVoucher = (long) 0;
		if(startDate == null && endDate == null) {
			return lstReportSumShowListDTOs;
		}
		List<Date> lstDates = getDaysBetweenDates(startDate, endDate);
		for (Date date : lstDates) {
			ReportSumShowDTO reportSumShowDTO = new ReportSumShowDTO();
			if (!invoiceDetailedRepository.getInvoiceDetailedServiceWithDate(date, date).isEmpty()) {
				totalAmountService = invoiceDetailedRepository.sumAmoutOfServiceWithDate(date, date);
			}
			if (!invoiceDetailedRepository.getInvoiceDetailedMedicineSaleWithDate(date, date).isEmpty()) {
				totalAmountMedicineSale = invoiceDetailedRepository.sumAmoutOfMedicineSaleWithDate(date, date);
			}
			if (!receiptRepository.getAmoutOfReceiptWithDate(date, date).isEmpty()) {
				totalAmountReceipt = receiptRepository.sumAmoutOfReceiptWithDate(date, date);
			}
			if (!debtPaymentSlipRepository.gettotalAmountDebtPaymentWithDate(date, date).isEmpty()) {
				totalAmountDebtPayment = debtPaymentSlipRepository.sumAmoutOftotalAmountDebtPaymentWithDate(date, date);
			}
			if (!debtPaymentSlipRepository.gettotalAmountDebtReceivableWithDate(date, date).isEmpty()) {
				totalAmountDebtReceivable = debtPaymentSlipRepository.sumAmoutOftotalAmountDebtReceivableWithDate(date,
						date);
			}
			if(!receiptVoucherRepository.getReceiptVoucherWithDate(date, date).isEmpty()) {
				totalAmountReceiptVoucher = receiptVoucherRepository.sumAmoutOfReceiptVoucherWithDate(date, date);
			}
			if(!paymentVoucherRepository.getPaymentVoucherWithDate(date, date).isEmpty()) {
				totalAmountPaymentVoucher = paymentVoucherRepository.sumAmoutOfPaymentVoucherWithDate(date, date);
			}
			Long totalReceived = totalAmountMedicineSale + totalAmountService + totalAmountDebtReceivable + totalAmountReceiptVoucher;
			Long totalPaymented = totalAmountReceipt + totalAmountDebtPayment + totalAmountPaymentVoucher;
			reportSumShowDTO.setTotalAmountDebtPayment(totalAmountDebtPayment);
			reportSumShowDTO.setTotalAmountDebtReceivable(totalAmountDebtReceivable);
			reportSumShowDTO.setTotalAmountMedicineSale(totalAmountMedicineSale);
			reportSumShowDTO.setTotalAmountReceipt(totalAmountReceipt);
			reportSumShowDTO.setTotalAmountService(totalAmountService);
			reportSumShowDTO.setTotalReceived(totalReceived);
			reportSumShowDTO.setTotalPaymented(totalPaymented);
			ReportSumShowListDTO reportSumShowListDTO = new ReportSumShowListDTO();
			reportSumShowListDTO.setTime(formatter.format(date));
			reportSumShowListDTO.setReportSumShowDTO(reportSumShowDTO);
			lstReportSumShowListDTOs.add(reportSumShowListDTO);
		}
		return lstReportSumShowListDTOs;
	}

	public List<ReportSumShowListDTO> showReportAccordingMonth(Integer year) {
		Long totalAmountMedicineSale = (long) 0;
		Long totalAmountService = (long) 0;
		Long totalAmountReceipt = (long) 0;
		Long totalAmountDebtPayment = (long) 0;
		Long totalAmountDebtReceivable = (long) 0;
		Long totalAmountReceiptVoucher = (long) 0;
		Long totalAmountPaymentVoucher = (long) 0;
		String month = null;
		List<ReportSumShowListDTO> lstReportSumShowListDTOs = new ArrayList<>();
		for (int index = 1; index < 13; index++) {
			ReportSumShowDTO reportSumShowDTO = new ReportSumShowDTO();
			month = "Tháng" + " " + index;
			if (!invoiceDetailedRepository.getInvoiceDetailedServiceWithMonth(index, year).isEmpty()) {
				totalAmountService = invoiceDetailedRepository.sumAmoutOfServiceWithMonth(index, year);
			}
			if (!invoiceDetailedRepository.getInvoiceDetailedMedicineSaleWithMonth(index, year).isEmpty()) {
				totalAmountMedicineSale = invoiceDetailedRepository.sumAmoutOfMedicineSaleWithMonth(index, year);
			}
			if (!receiptRepository.getAmoutOfReceiptWithMonth(index, year).isEmpty()) {
				totalAmountReceipt = receiptRepository.sumAmoutOfReceiptWithMonth(index, year);
			}
			if (!debtPaymentSlipRepository.gettotalAmountDebtPaymentWithMonth(index, year).isEmpty()) {
				totalAmountDebtPayment = debtPaymentSlipRepository.sumAmoutOftotalAmountDebtPaymentWithMonth(index,
						year);
			}
			if (!debtPaymentSlipRepository.gettotalAmountDebtReceivableWithMonth(index, year).isEmpty()) {
				totalAmountDebtReceivable = debtPaymentSlipRepository
						.sumAmoutOftotalAmountDebtReceivableWithMonth(index, year);
			}
			if(!receiptVoucherRepository.getReceiptVoucherWithMonth(index, year).isEmpty()) {
				totalAmountReceiptVoucher = receiptVoucherRepository.sumAmoutOfReceiptVoucherWithMonth(index, year);
			}
			if(!paymentVoucherRepository.getPaymentVoucherWithMonth(index, year).isEmpty()) {
				totalAmountPaymentVoucher = paymentVoucherRepository.sumAmoutOfPaymentVoucherWithMonth(index, year);
			}
			Long totalReceived = totalAmountMedicineSale + totalAmountService + totalAmountDebtReceivable + totalAmountReceiptVoucher;
			Long totalPaymented = totalAmountReceipt + totalAmountDebtPayment + totalAmountPaymentVoucher;
			reportSumShowDTO.setTotalAmountDebtPayment(totalAmountDebtPayment);
			reportSumShowDTO.setTotalAmountDebtReceivable(totalAmountDebtReceivable);
			reportSumShowDTO.setTotalAmountMedicineSale(totalAmountMedicineSale);
			reportSumShowDTO.setTotalAmountReceipt(totalAmountReceipt);
			reportSumShowDTO.setTotalAmountService(totalAmountService);
			reportSumShowDTO.setTotalReceived(totalReceived);
			reportSumShowDTO.setTotalPaymented(totalPaymented);

			ReportSumShowListDTO reportSumShowListDTO = new ReportSumShowListDTO();
			reportSumShowListDTO.setTime(month);
			reportSumShowListDTO.setReportSumShowDTO(reportSumShowDTO);
			lstReportSumShowListDTOs.add(reportSumShowListDTO);
		}
		return lstReportSumShowListDTOs;
	}

	public List<ReportSumShowListDTO> showReportAccordingQuarter(Integer year) {
		Long totalAmountMedicineSale = (long) 0;
		Long totalAmountService = (long) 0;
		Long totalAmountReceipt = (long) 0;
		Long totalAmountDebtPayment = (long) 0;
		Long totalAmountDebtReceivable = (long) 0;
		Long totalAmountReceiptVoucher = (long) 0;
		Long totalAmountPaymentVoucher = (long) 0;
		String quarter = null;
		List<ReportSumShowListDTO> lstReportSumShowListDTOs = new ArrayList<>();
		for (int index = 1; index < 5; index++) {
			ReportSumShowDTO reportSumShowDTO = new ReportSumShowDTO();
			quarter = "Quý" + " " + index;
			if (!invoiceDetailedRepository.getInvoiceDetailedServiceWithQuater(index, year).isEmpty()) {
				totalAmountService = invoiceDetailedRepository.sumAmoutOfServiceWithQuater(index, year);
			}
			if (!invoiceDetailedRepository.getInvoiceDetailedMedicineSaleWithQuater(index, year).isEmpty()) {
				totalAmountMedicineSale = invoiceDetailedRepository.sumAmoutOfMedicineSaleWithQuater(index, year);
			}
			if (!receiptRepository.getAmoutOfReceiptWithQuater(index, year).isEmpty()) {
				totalAmountReceipt = receiptRepository.sumAmoutOfReceiptWithQuater(index, year);
			}
			if (!debtPaymentSlipRepository.gettotalAmountDebtPaymentWithQuarter(index, year).isEmpty()) {
				totalAmountDebtPayment = debtPaymentSlipRepository.sumAmoutOftotalAmountDebtPaymentWithQuarter(index,
						year);
			}
			if (!debtPaymentSlipRepository.gettotalAmountDebtReceivableWithQuarter(index, year).isEmpty()) {
				totalAmountDebtReceivable = debtPaymentSlipRepository
						.sumAmoutOftotalAmountDebtReceivableWithQuarter(index, year);
			}
			if(!receiptVoucherRepository.getReceiptVoucherWithQuater(index, year).isEmpty()) {
				totalAmountReceiptVoucher = receiptVoucherRepository.sumAmoutOfReceiptVoucherWithQuater(index, year);
			}
			if(!paymentVoucherRepository.getPaymentVoucherWithQuater(index, year).isEmpty()) {
				totalAmountPaymentVoucher = paymentVoucherRepository.sumAmoutOfPaymentVoucherWithQuater(index, year);
			}
			Long totalReceived = totalAmountMedicineSale + totalAmountService + totalAmountDebtReceivable + totalAmountReceiptVoucher;
			Long totalPaymented = totalAmountReceipt + totalAmountDebtPayment + totalAmountPaymentVoucher;
			reportSumShowDTO.setTotalAmountDebtPayment(totalAmountDebtPayment);
			reportSumShowDTO.setTotalAmountDebtReceivable(totalAmountDebtReceivable);
			reportSumShowDTO.setTotalAmountMedicineSale(totalAmountMedicineSale);
			reportSumShowDTO.setTotalAmountReceipt(totalAmountReceipt);
			reportSumShowDTO.setTotalAmountService(totalAmountService);
			reportSumShowDTO.setTotalReceived(totalReceived);
			reportSumShowDTO.setTotalPaymented(totalPaymented);

			ReportSumShowListDTO reportSumShowListDTO = new ReportSumShowListDTO();
			reportSumShowListDTO.setTime(quarter);
			reportSumShowListDTO.setReportSumShowDTO(reportSumShowDTO);
			lstReportSumShowListDTOs.add(reportSumShowListDTO);
		}
		return lstReportSumShowListDTOs;
	}

	public List<ReportSumShowListDTO> showReportAccordingYear() {
		Long totalAmountMedicineSale = (long) 0;
		Long totalAmountService = (long) 0;
		Long totalAmountReceipt = (long) 0;
		Long totalAmountDebtPayment = (long) 0;
		Long totalAmountDebtReceivable = (long) 0;
		Long totalAmountReceiptVoucher = (long) 0;
		Long totalAmountPaymentVoucher = (long) 0;
		String year = null;
		List<ReportSumShowListDTO> lstReportSumShowListDTOs = new ArrayList<>();
		for (Integer i : invoiceDetailedRepository.getListYear()) {
			ReportSumShowDTO reportSumShowDTO = new ReportSumShowDTO();
			year = "Năm" + " " + i.toString();
			if (!invoiceDetailedRepository.getInvoiceDetailedServiceWithYear(i).isEmpty()) {
				totalAmountService = invoiceDetailedRepository.sumAmoutOfServiceWithYear(i);
			}
			if (!invoiceDetailedRepository.getInvoiceDetailedMedicineSaleWithYear(i).isEmpty()) {
				totalAmountMedicineSale = invoiceDetailedRepository.sumAmoutOfMedicineSaleWithYear(i);
			}
			if (!receiptRepository.getAmoutOfReceiptWithYear(i).isEmpty()) {
				totalAmountReceipt = receiptRepository.sumAmoutOfReceiptWithYear(i);
			}
			if (!debtPaymentSlipRepository.gettotalAmountDebtPaymentWithYear(i).isEmpty()) {
				totalAmountDebtPayment = debtPaymentSlipRepository.sumAmoutOftotalAmountDebtPaymentWithYear(i);
			}
			if (!debtPaymentSlipRepository.gettotalAmountDebtReceivableWithYear(i).isEmpty()) {
				totalAmountDebtReceivable = debtPaymentSlipRepository.sumAmoutOftotalAmountDebtReceivableWithYear(i);
			}
			if(!receiptVoucherRepository.getReceiptVoucherWithYear(i).isEmpty()) {
				totalAmountReceiptVoucher = receiptVoucherRepository.sumAmoutOfReceiptVoucherWithYear(i);
			}
			if(!paymentVoucherRepository.getPaymentVoucherWithYear(i).isEmpty()) {
				totalAmountPaymentVoucher = paymentVoucherRepository.sumAmoutOfPaymentVoucherWithYear(i);
			}
			Long totalReceived = totalAmountMedicineSale + totalAmountService + totalAmountDebtReceivable + totalAmountReceiptVoucher;
			Long totalPaymented = totalAmountReceipt + totalAmountDebtPayment + totalAmountPaymentVoucher;
			reportSumShowDTO.setTotalAmountDebtPayment(totalAmountDebtPayment);
			reportSumShowDTO.setTotalAmountDebtReceivable(totalAmountDebtReceivable);
			reportSumShowDTO.setTotalAmountMedicineSale(totalAmountMedicineSale);
			reportSumShowDTO.setTotalAmountReceipt(totalAmountReceipt);
			reportSumShowDTO.setTotalAmountService(totalAmountService);
			reportSumShowDTO.setTotalReceived(totalReceived);
			reportSumShowDTO.setTotalPaymented(totalPaymented);

			ReportSumShowListDTO reportSumShowListDTO = new ReportSumShowListDTO();
			reportSumShowListDTO.setTime(year);
			reportSumShowListDTO.setReportSumShowDTO(reportSumShowDTO);
			lstReportSumShowListDTOs.add(reportSumShowListDTO);
		}
		return lstReportSumShowListDTOs;
	}

	public List<Integer> getListYears() {
		return invoiceDetailedRepository.getListYear();
	}

	public List<ReportSumShowListDTO> showReportSum(Date startDate, Date endDate, Integer year, Integer type) {
		List<ReportSumShowListDTO> lstReportSumShowListDTOs = new ArrayList<>();
		if (type == 1) {
			lstReportSumShowListDTOs = showReportAccordingDate(startDate, endDate);
		}
		if (type == 2) {
			lstReportSumShowListDTOs = showReportAccordingMonth(year);
		}
		if (type == 3) {
			lstReportSumShowListDTOs = showReportAccordingQuarter(year);
		}
		if (type == 4) {
			lstReportSumShowListDTOs = showReportAccordingYear();
		}
		return lstReportSumShowListDTOs;

	}

}
