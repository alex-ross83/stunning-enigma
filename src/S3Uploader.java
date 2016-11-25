import java.io.File;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

public class S3Uploader {
	private static Upload upload;
	public static void WaitForCompletion() throws AmazonServiceException, AmazonClientException, InterruptedException{
		if(upload != null)
		{
			System.out.println("Waiting...");
			upload.waitForCompletion();
		}
	}
	
	public static void UploadFileNonBlocking(String bucketName, File targetFile, AWSCredentials credentials) {
		// TODO Auto-generated method stub
		try{
			TransferManager tm = new TransferManager(credentials);
			ProgressListener pl = new ProgressListener(){
				public void progressChanged(ProgressEvent progressEvent){
					System.out.println("Progress Event: " + progressEvent.getEventType());
				}
			};
			ProgressListener.ExceptionReporter er = ProgressListener.ExceptionReporter.wrap(pl);
			upload = tm.upload(bucketName, targetFile.getName(), targetFile);
			// While the transfer is processing, you can work with the transfer object
			upload.addProgressListener(er);
		} catch (AmazonServiceException ase) {
			System.out.println("Service Exception Catched");
            throw ase;
        } catch (AmazonClientException ace) {
        	System.out.println("Client Exception Catched");
            throw ace;
        } catch(Exception e){
        	System.out.println("Exception Catched");
        	throw e;
        }
		
	}

}
