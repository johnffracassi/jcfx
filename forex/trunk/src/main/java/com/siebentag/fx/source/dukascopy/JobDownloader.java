package com.siebentag.fx.source.dukascopy;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.siebentag.fx.source.DataDownloadJob;

@Component
public class JobDownloader extends JFrame
{
	public static final int QUEUE_SIZE = 25;
	
	private JCheckBox perpetual = new JCheckBox("Refresh Job List");
	
	private DefaultListModel listModel = new DefaultListModel();

	public static void main(String[] args)
    {
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
	    JobDownloader app = (JobDownloader)ctx.getBean("jobDownloader");
	    app.run();
    }
	
	public void run()
	{
		try
		{
			init();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(380, 380);
			setLocationByPlatform(true);
			setVisible(true);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.exit(ERROR);
		}
	}
	
	private void init() throws Exception
	{
		setLayout(new BorderLayout());
		
		final JPanel topButtons = new JPanel(new FlowLayout(3));
		final JButton btnExecute = new JButton("Execute");
		final JButton btnDownload = new JButton("Load Jobs");
		topButtons.add(btnDownload);
		topButtons.add(btnExecute);
		topButtons.add(perpetual);
		
		
		final JList list = new JList(listModel);
		final JScrollPane scrollPane = new JScrollPane(list);
		
		add(topButtons, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(new StatsPanel(), BorderLayout.SOUTH);
		
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeLoadJobsWorker();
			}
		});

		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeWorker();
			}
		});
	}

	private SwingWorker<String,Void> createLoadJobsWorker()
	{
		return new SwingWorker<String,Void>() {
            protected String doInBackground() throws Exception 
            {
            	loadJobs();
				return "ok";
            }
		};
	}
	
	private void executeLoadJobsWorker()
	{
		createLoadJobsWorker().execute();
	}
	
	private void loadJobs()
	{
		try
		{
			List<DataDownloadJob> jobs = JobManager.fetchJobs(QUEUE_SIZE);
			
			for(DataDownloadJob job : jobs)
			{
				listModel.addElement(job);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	private SwingWorker<String,Void> createExecuteWorker()
	{
		return new SwingWorker<String,Void>() {
            protected String doInBackground() throws Exception 
            {
            	if(listModel.size() == 0 && perpetual.isSelected())
            	{
            		loadJobs();
            	}
            	
        		if(listModel.size() > 0)
        		{
        			DataDownloadJob job = (DataDownloadJob)listModel.remove(0);

        			StatsPanel.addExecuted();
        			
	        		job.setStatus(TaskStatus.Running.toString());
	        		JobManager.updateJob(job);
	
	        		CandleLoaderTask loadCandleTask = new CandleLoaderTask(null);
	        		TickLoaderTask loadTickTask = new TickLoaderTask(loadCandleTask);
	        		DukascopyParserTask parseTask = new DukascopyParserTask(loadTickTask);
	        		ZipFileReaderTask readTask = new ZipFileReaderTask(parseTask);
	        		FileWriterTask writeTask = new FileWriterTask(readTask);
	        		DownloadTask downloadTask = new DownloadTask(job, writeTask);        		

	        		// set the jobIds
	        		loadTickTask.setJobId(job.getJobId());
	        		loadCandleTask.setJobId(job.getJobId());
	        		
	        		downloadTask.execute(null);        		
	        		
	        		job.setStatus(downloadTask.getStatus().toString());
	        		JobManager.updateJob(job);
	        		
	        		System.out.println("Job #" + job.getJobId() + " " + downloadTask.getStatus() + "!");
	        		
	        		if(downloadTask.getStatus() == TaskStatus.Complete)
	        			StatsPanel.addCompleted();
	        		else if(downloadTask.getStatus() == TaskStatus.Failed)
	        			StatsPanel.addFailed();
	        		
	        		executeWorker();
        		}
        		
        		return "ok";
            }
		};
	}
	
	private void executeWorker()
	{
		createExecuteWorker().execute();
	}
}
