/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlks.BUS;

import java.util.ArrayList;
import qlks.DAO.HoaDonDAO;
import qlks.DAO.ThongKeDAO;
import qlks.DTO.HoaDon;

/**
 *
 * @author admin
 */
public class ThongKeBUS {
    private ThongKeDAO tkDAO;
    private ArrayList<HoaDon> dstk;
    
    public ThongKeBUS(){
        tkDAO = new ThongKeDAO();
    }
    
    public ArrayList<HoaDon> thongKe(){
        return tkDAO.thongKe();
    }
    
    public ArrayList<HoaDon> thongKeNgayThanhToan(String tu, String den){
        return tkDAO.thongKeNgayThanhToan(tu, den);
    }
    
    public ArrayList<HoaDon> thongKeNgayThue(String tu, String den){
        return tkDAO.thongKeNgayThue(tu, den);
    }
}
