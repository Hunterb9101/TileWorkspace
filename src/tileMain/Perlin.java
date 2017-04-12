package tileMain;

import java.util.Random;

public class Perlin {
	
	float[][] scaleNBias(float[][] baseNoise, double size,double bias){
		 for (int i = 0; i < baseNoise.length; i++) {
		        for (int j = 0; j < baseNoise[i].length; j++){
		            baseNoise[i][j]= (float) Math.pow(baseNoise[i][j], 1/size);
		            baseNoise[i][j]+=bias;
		        }
		    }
		 return baseNoise;
	}
	
	float[][] generateWhiteNoise(int width, int height) {
	    Random random = new Random();
	    float[][] noise = new float[width][height];
	    
	    for (int i = 0; i < noise.length; i++) {
	        for (int j = 0; j < noise[i].length; j++){
	            noise[i][j] = (float)random.nextDouble();
	        }
	    }
	 
	    return noise;
	}
	
	float[][] generateSmoothNoise(float[][] baseNoise, int octave){
		int width = baseNoise.length;
		int height = baseNoise[0].length;
		float[][] smoothNoise = baseNoise;
	 
		int samplePeriod = (int) Math.pow(2,octave); // calculates 2 ^ k
		float sampleFrequency = 1.0f / samplePeriod;
	   
		for (int i = 0; i < width; i++) {
	      //calculate the horizontal sampling indices
	      int sample_i0 = (i / samplePeriod) * samplePeriod;
	      int sample_i1 = (sample_i0 + samplePeriod) % width; //wrap around
	      float horizontal_blend = (i - sample_i0) * sampleFrequency;
	      for (int j = 0; j < height; j++){
	         //calculate the vertical sampling indices
	         int sample_j0 = (j / samplePeriod) * samplePeriod;
	         int sample_j1 = (sample_j0 + samplePeriod) % height; //wrap around
	         float vertical_blend = (j - sample_j0) * sampleFrequency;
	         
	         float top = interpolate(baseNoise[sample_i0][sample_j0],
	            baseNoise[sample_i1][sample_j0], horizontal_blend);
	 
	         float bottom = interpolate(baseNoise[sample_i0][sample_j1],
	            baseNoise[sample_i1][sample_j1], horizontal_blend);
	 
	         smoothNoise[i][j] = interpolate(top, bottom, vertical_blend);
	      }
	   }
	   return smoothNoise;
	}
	
	float interpolate(float x0, float x1, float alpha){
		return (float) ((float)(x0) * (float)(1 - alpha) + (float)(alpha * x1));
	}
	
	float[][] generateRidgedPerlinNoise(float[][] baseNoise, int octaveCount) {
	   int width = baseNoise.length;
	   int height = baseNoise[0].length;
	 
	   float[][][] smoothNoise = new float[octaveCount][][]; //an array of 2D arrays containing
	 
	   float persistance = 0.5f;
	 
	   //generate smooth noise
	   for (int i = 0; i<octaveCount; i++) {
	       smoothNoise[i] = generateSmoothNoise(baseNoise, i);
	   }
	 
	    float[][] perlinNoise = new float[width][height];
	    float amplitude = 1.0f;
	    float totalAmplitude = 0.0f;
	    //blend noise together
	    
	    for (int i = 0; i < width; i++) {
	          for (int j = 0; j < height; j++) {
	             perlinNoise[i][j] = 0.0f;
	          }
	    }
	    
	    for (int octave = octaveCount - 1; octave >= 0; octave--) {
	       amplitude *= persistance;
	       totalAmplitude += amplitude;
	      
	       for (int i = 0; i < width; i++) {
	          for (int j = 0; j < height; j++) {
	             perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;             
	          }
	       }
	    }
	 	
	   //normalization
	   for (int i = 0; i < width; i++) {
	      for (int j = 0; j < height; j++) {
	         perlinNoise[i][j] /= totalAmplitude;
	         perlinNoise[i][j] = (float) Math.abs(2*perlinNoise[i][j] - 1);
	      }
	   }
	   return perlinNoise;
	}
	
	float[][] generatePerlinNoise(float[][] baseNoise, int octaveCount) {
		   int width = baseNoise.length;
		   int height = baseNoise[0].length;
		 
		   float[][][] smoothNoise = new float[octaveCount][][]; //an array of 2D arrays containing
		 
		   float persistance = 0.5f;
		 
		   //generate smooth noise
		   for (int i = 0; i<octaveCount; i++) {
		       smoothNoise[i] = generateSmoothNoise(baseNoise, i);
		   }
		 
		    float[][] perlinNoise = new float[width][height];
		    float amplitude = 1.0f;
		    float totalAmplitude = 0.0f;
		    //blend noise together
		    
		    for (int i = 0; i < width; i++) {
		          for (int j = 0; j < height; j++) {
		             perlinNoise[i][j] = 0.0f;
		          }
		    }
		    
		    for (int octave = octaveCount - 1; octave >= 0; octave--) {
		       amplitude *= persistance;
		       totalAmplitude += amplitude;
		      
		       for (int i = 0; i < width; i++) {
		          for (int j = 0; j < height; j++) {
		             perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;             
		          }
		       }
		    }
		 	
		   //normalization
		   for (int i = 0; i < width; i++) {
		      for (int j = 0; j < height; j++) {
		         perlinNoise[i][j] /= totalAmplitude;
		      }
		   }
		   return perlinNoise;
		}
	
	// Octaves - # Continents, Noise Detail
	// Dropoff - Size of landmasses/water
	public float[][] generateMultiOctavePerlinNoise(int octaves, double dropoff, int width, int height){
		float[][][]noise = new float[octaves][width][height];
		double persistence = 1;
		for(int i = octaves - 1; i > 0;i--){
			noise[i] = generatePerlinNoise(generateWhiteNoise(width,height),octaves - i);
		}
		
		float[][] multiOctave = new float[width][height];
		float totalAmplitude = 0.0f;
		for(int a= 0; a<noise.length; a++){
			persistence*= dropoff;
			totalAmplitude+=persistence;
			for(int i = 0; i<multiOctave.length; i++){
				for(int j = 0; j<multiOctave[i].length; j++){
					multiOctave[i][j] += (float)(noise[a][i][j])*(float)(persistence);
				}
			}
		}
		
		for (int i = 0; i < width; i++) {
		      for (int j = 0; j < height; j++) {
		         multiOctave[i][j] /= totalAmplitude;
		      }
		   }
		return multiOctave;
		
	}
}
