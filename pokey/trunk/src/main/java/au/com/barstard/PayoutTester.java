package au.com.barstard;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import au.com.barstard.gamestate.GameStateModel;
import au.com.barstard.payline.PayLineProcessor;
import au.com.barstard.reel.ReelController;
import au.com.barstard.reel.ReelModel;
import au.com.barstard.symbol.SymbolModel;

@Component
public class PayoutTester
{
    @Autowired
    private GameStateModel model;
    
    @Autowired
    private ReelController reelController;
    
    @Autowired
    private PayLineProcessor paylineProcessor;
    
    private int[] spinsForReel;
    private int[] reelPosition;
    private List<ReelModel> reels;
    
    public static void main(String[] args)
    {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("blokey.xml");
        PayoutTester tester = ctx.getBean(PayoutTester.class);
        tester.run();
    }
    
    private void run()
    {
        int spins = 50000;
        int balance = 0;
        int investment = 0;
        model.setLinesPlayed(25);
        
        for(int i=0; i<spins; i++)
        {
            investment += model.getLinesPlayed();
            SymbolModel[][] activeSymbols = spin();
//            outputDisplay(activeSymbols);
            int[] wins = paylineProcessor.calculateWin(activeSymbols);
            
            int total = 0;
            for(int j=0; j<wins.length; j++)
            {
                total += wins[j];
            }
            
            balance += total;
        }
        
        System.out.printf("Spins:    %d \n", spins);
        System.out.printf("Paid in:  %d \n", investment);
        System.out.printf("Paid out: %d \n", balance);
        System.out.printf("Avg Win:  %1.1f \n", (double)balance / spins);
        System.out.printf("Avg Win:  %1.2f%% \n", (double)balance / investment * 100.0);
    }
    
    private void outputDisplay(SymbolModel[][] activeSymbols)
    {
        System.out.println("=====");
        for(int i=0; i<activeSymbols[0].length; i++)
        {
            for(int j=0; j<activeSymbols.length; j++)
            {
                System.out.print(activeSymbols[j][i]);
            }
            System.out.println();
        }
    }
    
    @PostConstruct
    private void init()
    {
        reels = reelController.getReels();
        
        spinsForReel = new int[reels.size()];
        reelPosition = new int[reels.size()];
        
        for(int i=0; i<spinsForReel.length; i++)
        {
            spinsForReel[i] = 7 + i*3;
            reelPosition[i] = (int)(Math.random() * reels.get(i).size());
        }
    }

    public SymbolModel[][] spin()
    {
        for (int i = 0; i < 5; i++)
        {
            reelPosition[i] = (reelPosition[i] + spinsForReel[i] + (int)(0.5 + Math.random()) % reels.get(i).size());
        }
        
        return getActiveSymbols();
    }

    private SymbolModel[][] getActiveSymbols()
    {
        SymbolModel[][] activeSymbols = new SymbolModel[reels.size()][];
        for(int i=0; i<activeSymbols.length; i++)
        {
            activeSymbols[i] = getActiveSymbolsForReel(i);
        }
        return activeSymbols;
    }
    
    private SymbolModel[] getActiveSymbolsForReel(int reelIdx)
    {
        int symbolIdx = reelPosition[reelIdx];
        int reelSize = reels.get(reelIdx).size();
        
        SymbolModel[] activeSymbols = new SymbolModel[3];
        activeSymbols[0] = reels.get(reelIdx).getSymbols().get((symbolIdx + 0) % reelSize);
        activeSymbols[1] = reels.get(reelIdx).getSymbols().get((symbolIdx + 1) % reelSize);
        activeSymbols[2] = reels.get(reelIdx).getSymbols().get((symbolIdx + 2) % reelSize);

        return activeSymbols;
    }
}
