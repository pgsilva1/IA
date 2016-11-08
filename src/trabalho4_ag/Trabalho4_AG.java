package trabalho4_ag;

import java.util.Random;


public class Trabalho4_AG {
    private int[][] populacao;
    private int[][] distancias;
    private int[] avaliacao;
    private int[] select;

    public Trabalho4_AG() {
        int i,j;
        populacao = new int[50][15];
        distancias = new int[50][15];
        avaliacao = new int[50];
        
        for(i = 0;i<50;i++){
            avaliacao[i] = 15;
        }

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
         
         for(i = 0; i < 15;i++){
            for(j = 0;j < 15;j++){
                System.out.print(distancias[i][j] + "\t");
            }
             System.out.println("");
        }
    }
    
    void avaliacao(){//ou fitness
        int i, j;
        int[] cidades;
        
        for(i = 0; i < 50;i++){
            cidades = new int[15]; //Zerando a cada linha
            
            for(j = 0;j < 15;j++){
                if(cidades[populacao[i][j] - 1] > 0){ //Já tem essa cidade no vetor
                   avaliacao[i]--; //0(ruim) a 15(bom)
                }
                cidades[populacao[i][j] - 1]++;
            }
            
        } 
    }
    
    int soma(){
        int soma = 0;
        for(int i = 0; i< avaliacao.length; i++)
            soma += avaliacao[i];
        
        return soma;
    }
    
    int[] min(int array[]){
        int[] minimo = new int[2];
        minimo[0] = array[0];
        minimo[1] = 0;
        
        for(int i = 0;i < array.length;i++){
                
            if(array[i] < minimo[0]){
                minimo[0] =  array[i];
                minimo[1] = i;
            }
        }
        
        return minimo;
    }
    
    void roleta_e_elitismo(float Pelite){ //Potencial de elite
        int nIndiv = avaliacao.length;
        int total = soma();
        int fitness[] = new int[50];
        int Nelite,aux_fit[],i,j,aux_pos;
        int[] aux_min_pos = new int[2];
        Random random = new Random();
        
        for(i = 0; i<50; i++){
            fitness[i] = avaliacao[i]/total;
        }
         Nelite = Math.round((nIndiv*Pelite));
         aux_fit = fitness;
         select = new int[nIndiv];
         
         for(i = 1; i < Nelite; i++){
             aux_min_pos = min(aux_fit);
             aux_pos = aux_min_pos[1];
             aux_fit[aux_pos] = 0;
             select[i] = aux_pos;
         }
         
         for(i = Nelite + 1; i < nIndiv; i++){
             int roleta,soma = 0;
             roleta = random.nextInt(nIndiv);
             
             for(j = 1; j < nIndiv; j++){
                 soma = soma + avaliacao[j];
                 if(soma >= roleta){
                     System.out.println(avaliacao[j]);
                     select[i] = j;
                     break;
                 }
             }
         }
    }
    
    void cruzamento(float Pelite){
        int nIndividuos = select.length;
        int pai,mae,cp,lInd = 15;
        int nElite = Math.round(nElite*Pelite);
        for(int i = 1; i < Math.round(nIndividuos/2);i++){
            pai = Math.round();
        }
    }
    
    public static void main(String[] args) {
        Trabalho4_AG a = new Trabalho4_AG();
        a.geraPopECod();
        a.distancias();
        int i,j;
        a.avaliacao();
        a.roleta_e_elitismo((float)0.1);
        
    }
    
}
