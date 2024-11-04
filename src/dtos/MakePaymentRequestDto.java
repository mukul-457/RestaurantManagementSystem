package dtos;

public class MakePaymentRequestDto {
    private long billId;

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }
}