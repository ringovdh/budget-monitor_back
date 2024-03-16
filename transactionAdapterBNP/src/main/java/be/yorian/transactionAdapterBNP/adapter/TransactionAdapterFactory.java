package be.yorian.transactionAdapterBNP.adapter;


/**
 * Factory for creating instances of TransactionAdapter.
 */
public final class TransactionAdapterFactory {

    private TransactionAdapterFactory() { }

    /**
     * Creates an instance of TransactionAdapter.
     * @return the new instance
     */
    public static TransactionAdapter createTransactionAdapter() {
        return new TransactionAdapterImpl();
    }
}
