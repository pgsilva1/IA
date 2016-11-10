package trabalho4_ag;

import java.util.ArrayList;
import java.util.Random;


public class Trabalho4_AG {
    private int[][] populacao;
    private int[][] distancias;
    private int[] avaliacao;
    private int[] select;
    private int [][] cross;
    //private ArrayList<Integer> select = new ArrayList<>();

    public Trabalho4_AG() {
        //int i,j;
        populacao = new int[50][15];
        distancias = new int[50][15];
        avaliacao = new int[50];
        cross = new int[50][15];

    }
    
    

    void geraPopECod(){
        int i,j;
        Random random = new Random();
        for(i = 0;i < 50;i++){
            for(j = 0;j < 15;j++){
                populacao[i][j] = random.nextInt(15) + 1;
                //System.out.print(populacao[i][j] + "\t");
            }
            //System.out.println("");
        }
    }
    
    void distancias(){
         int i,j;
         for(i = 0;i < 15;i++){
             distancias[i][i] = 0;
            for(j = i+1;j < 15;j++){        //Diagonal
                distancias[i][j] = i + j;   //Diagonal Superior
                distancias[j][i] = i + j;   //Diagonal Inferior
            }
         }
         
//         for(i = 0; i < 15;i++){
//            for(j = 0;j < 15;j++){
//                System.out.print(distancias[i][j] + "\t");
//            }
//             System.out.println("");
//        }
    }
    
    void avaliacao(){//ou fitness
        int i, j;
        int[] cidades;// = new int[15];
        
//        for(i = 0; i< 15; i++){
//            cidades[i] = 0;
//            //System.out.println("" + cidades[i]);
//        }
//        
        for(i = 0;i<50;i++){
            avaliacao[i] = 15;
        }
        
        for(i = 0; i < 50;i++){
             cidades = new int[15];//Zerando a cada linha
            for(j = 0;j < 15;j++){
                if(cidades[populacao[i][j] - 1] > 0){ //Já tem essa cidade no vetor
                   avaliacao[i]--; //0(ruim) a 15(bom)
                }
                cidades[populacao[i][j] - 1]++;
                //System.out.print("--" + cidades[j]);
            }
            //System.out.print("--" + avaliacao[i]);
            //System.out.print("\n");
            //if(avaliacao[i] == 15)
                //System.out.println("O melhor");
            
            
        } 
        
    }
    
    
    int soma(){
        int soma = 0;
        for(int i = 0; i< avaliacao.length; i++)
            soma += avaliacao[i];
        
        return soma;
    }
    
    float[] max(float array[]){
        float[] minimo = new float[2];
        minimo[0] = array[0];
        minimo[1] = 0;
        
        for(int i = 0;i < array.length;i++){
                
            if(array[i] > minimo[0]){
                minimo[0] =  array[i];
                minimo[1] = i;
            }
        }
        
        return minimo;
    }
    
    void roleta_e_elitismo(float Pelite){ //Potencial de elite
        int nIndiv = avaliacao.length;
        int total = soma();
        float fitness[] = new float[50];
        float aux_fit[], aux_pos;
        int Nelite,i,j;
        float[] aux_min_pos = new float[2];
        Random random = new Random();
        
        //System.out.println("" + nIndiv);
        
        for(i = 0; i<50; i++){
            fitness[i] = (float)avaliacao[i]/total;
            //System.out.println("" +total);
        }
        
         Nelite = Math.round((nIndiv*Pelite));
         //System.out.println("" + nIndiv);
         aux_fit = fitness;
         select = new int[nIndiv];
         
         
         for(i = 0; i < Nelite; i++){
             aux_min_pos = max(aux_fit);
             aux_pos = aux_min_pos[1];
             //System.out.println("" + aux_pos);
             aux_fit[(int)aux_pos] = 1;
             select[i] = (int)aux_pos;
             //select.add(aux_pos);
         }
         
         for(i = Nelite + 1; i < nIndiv; i++){
             float roleta,soma = 0;
             roleta = random.nextFloat();
             //System.out.println("" + roleta);
             
             for(j = 0; j < nIndiv; j++){
                 soma = soma + fitness[j];
                 if(soma >= roleta){
                     //System.out.println(soma + " >= " + roleta);
                    select[i] = j;
                   //System.out.println("" + j);
                     
                     break;
                 }
             }
         }
         
         for(int k = 0; k<50; k++){
             //System.out.println("" + select[k] + "--- " + k);
         }
    }
    
    void cruzamento(float Pelite){
        Random rand = new Random();
        int nIndividuos = select.length;
        int pai,mae,cp,lInd = 15;
        int nElite = Math.round(nIndividuos*Pelite);
        //System.out.println("" + nElite);
        for(int i = 0; i < Math.round(nIndividuos/2);i++){
            pai = Math.round(rand.nextFloat()*(nIndividuos-1));
            mae = Math.round(rand.nextFloat()*(nIndividuos-1));
            cp = Math.round(rand.nextFloat()*(nIndividuos-1));
            //System.out.println("Mae: " + mae + " - Pai: " + pai);
            
            int[] filho1 = new int[15];
            int[] filho2 = new int[15];
            
            for(int j = 0; j < lInd; j++){
                if(j<=cp){
                    filho1[j] = populacao[pai][j];
                    filho2[j] = populacao[mae][j];
                }else{
                    filho1[j] = populacao[mae][j];
                    filho2[j] = populacao[pai][j];
                }   
            }
            
            for(int k = 0; k < 15; k++){
                cross[i][k] = filho1[k];
                //System.out.print(" -- " + filho1[k]);
                cross[i+Math.round(nIndividuos/2)][k] = filho2[k];
            }  
            //System.out.println(" ");
        }
        
        //System.out.println("" + nElite);
        
        for(int i = 0; i<nElite; i++){
                for(int j = 0; j < 15; j++){
                    cross[i][j] = populacao[select[i]][j];
                }
         }
        
        //populacao = cross;
    }
    
    void mutacao(float pmut, float pelit){
        int auxiliar;
        int nIndividuos = select.length;
        int nElite = Math.round(nIndividuos*pelit);
        Random rand = new Random();
        //System.out.println("" + nElite);
        
        for(int i = nElite+1; i < nIndividuos; i++){
            int primGene = rand.nextInt(15);
            int segGene = rand.nextInt(15);
            auxiliar = populacao[i][primGene];
            populacao[i][primGene] = populacao[i][segGene];
            populacao[i][segGene] = auxiliar;
        }
        populacao = cross;
    }
    
    void show(){
        for(int i = 0; i < 50 ;i++){
            for(int j = 0;j < 15;j++){
                System.out.print(populacao[i][j] + "\t");
            }
             System.out.println("");
        }
    }
    
    public static void main(String[] args) {
        int quantidade = 0;
        float pelite = (float)1.0;
        Trabalho4_AG a = new Trabalho4_AG();
        a.geraPopECod();
        a.distancias();
        do{
        //int i,j;
        a.avaliacao();
        a.roleta_e_elitismo(pelite);
        a.cruzamento(pelite);
        a.mutacao((float)0.2, pelite);
        a.show();
        quantidade++;
        System.out.println("------------------------------------------------------------------------------------------------------------");
        }while( quantidade < 50);
        
    }
    
}
