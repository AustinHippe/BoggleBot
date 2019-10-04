public class QueueUnderFlowException extends StackQueueException
{
/*
Brandon Q Tran
Dr Jarvis
CISC231
February 28, 2019
*/
	private static final long serialVersionUID = 1;
	public QueueUnderFlowException()
	{
		super();
	}
	public QueueUnderFlowException(String message)
	{
		super(message);
	}
}