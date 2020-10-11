/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlks.BUS;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import qlks.DAO.HoaDonDAO;
import qlks.DTO.HoaDon;
import qlks.DTO.HoaDonDTO;
import qlks.DTO.HopDongThuePhong;
import qlks.DTO.HopDongThuePhongDTO;
import qlks.DTO.NhanVien;
import qlks.DTO.NhanVienDTO;

/**
 *
 * @author admin
 */
public class HoaDonBUS {
    private HoaDonDAO hdDAO;
    
    public HoaDonBUS(){
        hdDAO = new HoaDonDAO();
    }
    
    public ArrayList<HoaDon> docDanhSachHoaDon(){
        return hdDAO.docDanhSachHoaDon();
    }
    
    public boolean themHoaDon(HoaDonDTO hdDTO, HoaDon hd){
        //Kiem tra so hoa don co bi trung
        for(HoaDon hdt: hdDTO.docDanhSachHoaDon()){
            if(hdt.getSoHoaDon().compareTo(hd.getSoHoaDon())==0){
                JOptionPane.showMessageDialog(null, "Trùng số hóa đơn.");
                return false;
            }
        }
        NhanVienDTO nvDTO = new NhanVienDTO();
        HopDongThuePhongDTO hdtpDTO = new HopDongThuePhongDTO();
        boolean mnv = false; //Ma nhan vien
        boolean shdtp = false; //So hop dong thue phong
        
        //tim nhan vien trong nhan vien DTO
        for(NhanVien nv: nvDTO.docDanhSachNhanVien()){
            if(nv.getMaNhanVien().compareTo(hd.getMaNhanVien())==0){
                mnv = true;
            }
        }
        //Tim so hop dong trong hop dong DTO
        for(HopDongThuePhong hdtp: hdtpDTO.docDanhSachHopDongThuePhong()){
            if(hdtp.getSoHopDongThuePhong().compareTo(hd.getSoHopDongThuePhong())==0){
                shdtp = true;
            }
        }
        //Bao loi new nhan vien chua ton tai
        if(mnv==false){
            JOptionPane.showMessageDialog(null, "Mã nhân viên chưa tồn tại");
            return false;
        }
        //Bao loi neu So hop dong chua ton tai
        if(shdtp==false){
            JOptionPane.showMessageDialog(null, "Số hợp đồng chưa tồn tại");
            return false;
        }
        //Them Hoa Don trong DTO va DAO
        hdDAO.themHoaDon(hd);
        hdDTO.themHoaDon(hd);
        return true;
    }
    
    public boolean suaHoaDon(HoaDonDTO hdDTO,HoaDon hd){
        int row = -1;
        //tim so hoa don trong DTO
        for(int i=0;i<hdDTO.docDanhSachHoaDon().size();i++){
            if(hd.getSoHoaDon().compareTo(hdDTO.docDanhSachHoaDon().get(i).getSoHoaDon())==0){
                row = i;
                break;
            }
        }
        
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Số hóa đơn không tồn tại");
            return false;
        }
        //Kiem tra co gi can sua ko
        if(hd.getSoHopDongThuePhong().compareTo(hdDTO.docDanhSachHoaDon().get(row).getSoHopDongThuePhong())==0 &&
                hd.getMaNhanVien().compareTo(hdDTO.docDanhSachHoaDon().get(row).getMaNhanVien())==0 &&
                hd.getNgayThanhToan().compareTo(hdDTO.docDanhSachHoaDon().get(row).getNgayThanhToan())==0 &&
                hd.getTienThuePhong()==hdDTO.docDanhSachHoaDon().get(row).getTienThuePhong() &&
                hd.getTienDichVu()==hdDTO.docDanhSachHoaDon().get(row).getTienDichVu() &&
                hd.getTienKhuyenMai()==hdDTO.docDanhSachHoaDon().get(row).getTienKhuyenMai() &&
                hd.getThue()==hdDTO.docDanhSachHoaDon().get(row).getThue() &&
                hd.getTongTien()==hdDTO.docDanhSachHoaDon().get(row).getTongTien()){
            JOptionPane.showMessageDialog(null, "Không có gì để sửa");
            return false;
        }
        //Sua neu o tren ko co loi gi
        int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn sửa hóa đơn này");
            if(chon==JOptionPane.YES_OPTION){
                hdDAO.suaHoaDon(hd, hdDTO.docDanhSachHoaDon().get(row).getSoHoaDon());
                hdDTO.suaHoaDon(hd, row);
                return true;
            }else{
                return false;
        }
    }
    
    public boolean xoaHoaDon(HoaDonDTO hdDTO,HoaDon hd){
        int row = -1;
        //tim so hoa don trong DTO
        for(int i=0;i<hdDTO.docDanhSachHoaDon().size();i++){
            if(hd.getSoHoaDon().compareTo(hdDTO.docDanhSachHoaDon().get(i).getSoHoaDon())==0){
                row = i;
                break;
            }
        }
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Số hóa đơn không tồn tại");
            return false;
        }
        //Xoa trong DAO va DTO, xoa luon chu ko giu lai
        int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa hóa đơn này");
            if(chon==JOptionPane.YES_OPTION){
                hdDAO.xoaHoaDon(hd.getSoHoaDon());
                hdDTO.xoaHoaDon(row);
                return true;
            }else{
                return false;
        }
    }
    
    //Dung de them hoa don tu file
    public boolean themHoaDonTuFile(HoaDonDTO hdDTO, HoaDon hd){
        //Kiem tra so hoa don co bi trung
        for(HoaDon hdt: hdDTO.docDanhSachHoaDon()){
            if(hdt.getSoHoaDon().compareTo(hd.getSoHoaDon())==0){
                return false;
            }
        }
        NhanVienDTO nvDTO = new NhanVienDTO();
        HopDongThuePhongDTO hdtpDTO = new HopDongThuePhongDTO();
        boolean mnv = false; //Ma nhan vien
        boolean shdtp = false; //So hop dong thue phong
        
        //tim nhan vien trong nhan vien DTO
        for(NhanVien nv: nvDTO.docDanhSachNhanVien()){
            if(nv.getMaNhanVien().compareTo(hd.getMaNhanVien())==0){
                mnv = true;
            }
        }
        //Tim so hop dong trong hop dong DTO
        for(HopDongThuePhong hdtp: hdtpDTO.docDanhSachHopDongThuePhong()){
            if(hdtp.getSoHopDongThuePhong().compareTo(hd.getSoHopDongThuePhong())==0){
                shdtp = true;
            }
        }
        //Bao loi new nhan vien chua ton tai
        if(mnv==false){
            return false;
        }
        //Bao loi neu So hop dong chua ton tai
        if(shdtp==false){
            return false;
        }
        //Them Hoa Don trong DTO va DAO
        hdDAO.themHoaDon(hd);
        hdDTO.themHoaDon(hd);
        return true;
    }
}
