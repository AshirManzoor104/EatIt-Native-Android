package com.example.eatit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CartHelper extends SQLiteOpenHelper {
    public CartHelper(Context context) {
        super(context, "shopping_cat", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="CREATE TABLE cart(cartId INTEGER PRIMARY KEY AUTOINCREMENT, productId INTEGER,productName TEXT," +
                "productDescription TEXT,productPrice INTEGER,productImage TEXT,productDiscount INTEGER,quantity INTEGER)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS cart");
        onCreate(sqLiteDatabase);

    }
    public long addItemToCart(Product product,int quantity){
        ContentValues cv=new ContentValues();
        cv.put("productId",product.getProductId());
        cv.put("productName",product.getProductName());
        cv.put("productPrice",product.getProductPrice());
        cv.put("productDescription",product.getProductDescription());
        cv.put("productDiscount",product.getProductDiscount());
        cv.put("productImage",product.getProductImage());
        cv.put("quantity",quantity);

        SQLiteDatabase db=getWritableDatabase();
        return db.insert("cart",null,cv);

    }
    public ArrayList<CartItem> getAllCartItems(){
        ArrayList<CartItem>cartItemList=new ArrayList<>();
        String query= "SELECT * FROM cart ORDER BY cartId";
        SQLiteDatabase  db=getReadableDatabase();
         Cursor c=db.rawQuery(query,null);
         while (c.moveToNext()){
             int idx=c.getColumnIndex("cartId");
             int cartId=c.getInt(idx);
             int productId=c.getInt(c.getColumnIndex("productId"));
             String productName=c.getString(c.getColumnIndex("productName"));
             String productDescription=c.getString(c.getColumnIndex("productDescription"));
             int productDiscount=c.getInt(c.getColumnIndex("productDiscount"));
             int productPrice=c.getInt(c.getColumnIndex("productPrice"));
             String productImage=c.getString(c.getColumnIndex("productImage"));
             int quantity=c.getInt(c.getColumnIndex("quantity"));

             Product pro=new Product();
             pro.setProductId(productId);
             pro.setProductName(productName);
             pro.setProductPrice(productPrice);
             pro.setProductDescription(productDescription);
             pro.setProductDiscount(productDiscount);
             pro.setProductImage(productImage);

             CartItem cartItem=new CartItem(cartId,pro,quantity);
             cartItemList.add(cartItem);


         }
         c.close();
         return cartItemList;

    }
    public  int updateItemInCart(int cartId,int quantity){
        ContentValues cv=new ContentValues();
        cv.put("quantity",quantity);
        SQLiteDatabase db=getWritableDatabase();
        return db.update("cart",cv,"cartId="+cartId,null);
    }
    public int deleteCartItem(int cartId){
        SQLiteDatabase db=getWritableDatabase();
        return db.delete("cart","cartId="+cartId,null);
    }
    public int deleteOrderItem(int orderId,int orderPrice){
        SQLiteDatabase db=getWritableDatabase();
        return db.delete("order","cartId="+orderId+"orderPrice="+orderPrice,null);
    }

    public int clearCart(){
        SQLiteDatabase db=getWritableDatabase();
        return db.delete("cart","1",null);
    }
    public int getNumberOfItemInCart(){
        String query="SELECT * FROM cart";
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery(query,null);
        int numberOfItems=c.getCount();
        c.close();
        return numberOfItems;

    }
    public int getTotalBillOfCart(){

        String query="SELECT SUM((productPrice - productDiscount) * quantity)as totalBill FROM cart";
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery(query,null);
        c.moveToFirst();
        int totalBill=c.getInt(c.getColumnIndex("totalBill"));
        c.close();
        return totalBill;


    }
    public boolean isProductInCart(int productId){

        String query="SELECT * FROM cart WHERE productId="+ productId ;
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.rawQuery(query,null);
        int numberOfRows=c.getCount();
        c.close();
        if (numberOfRows>0){
            return true;
        }else {
            return false;
        }
    }
}
