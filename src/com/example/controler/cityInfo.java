package com.example.controler;

public class cityInfo {  
    private int id; //��ϢID  
    private String title;   //��Ϣ����  
    private String details; //��ϸ��Ϣ  
    private int avatar; //ͼƬID  			     			   		  
    //��ϢID������  
    public void setId(int id) {    
	        this.id = id;  
	    }  
	    public int getId() {    
	        return id;  
	    }  
	    //����  
	    public void setTitle(String title) {    
	        this.title = title;  
	    }  
	   public String getTitle() {  
	        return title;    
	    }  
	      
	    //��ϸ��Ϣ  
	    public void setDetails(String info) {    
	        this.details = info;  
	   }  
	    public String getDetails() {    
	        return details;    
	    }  
	      
	    //ͼƬ  
	    public void setAvatar(int avatar) {    
	        this.avatar = avatar;  
	    }  
	public int getAvatar() {    
	     return avatar;  
	}  
} 