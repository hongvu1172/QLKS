/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlks.BUS;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import qlks.DAO.ChiTietNhapHangDAO;
import qlks.DTO.ChiTietNhapHang;
import qlks.DTO.ChiTietNhapHangDTO;
import qlks.DTO.MatHang;
import qlks.DTO.MatHangDTO;
import qlks.DTO.PhieuNhapHang;
import qlks.DTO.PhieuNhapHangDTO;

/**
 *
 * @author admin
 */
public class ChiTietNhapHangBUS {
    private ChiTietNhapHangDAO ctnhDAO;
    
    public ChiTietNhapHangBUS(){
        ctnhDAO = new ChiTietNhapHangDAO();
    }
    
    public ArrayList<ChiTietNhapHang> docDanhSachChiTietNhapHang(){
        return ctnhDAO.docDanhSachChiTietNhapHang();
    }
    
    public boolean themChiTietNhapHang(ChiTietNhapHangDTO ctnhDTO, ChiTietNhapHang ctnh){
        for(ChiTietNhapHang ctnht: ctnhDTO.docDanhSachChiTietNhapHang()){
            if(ctnht.getMaPhieuNhapHang().compareTo(ctnh.getMaPhieuNhapHang())==0 &&
                    ctnht.getMaMatHang().compareTo(ctnh.getMaMatHang())==0){
                JOptionPane.showMessageDialog(null, "Không thể thêm một mã mặt hàng trên cùng một mã phiếu nhâp hàng");
                return false;
            }
        }
        MatHangDTO kmDTO = new MatHangDTO();
        PhieuNhapHangDTO hdtpDTO = new PhieuNhapHangDTO();
        boolean mmh = false;
        boolean mpnh = false;
        for(MatHang mh: kmDTO.docDanhSachMatHang()){
            if(mh.getMaMatHang().compareTo(ctnh.getMaMatHang())==0){
                mmh = true;
            }
        }
        for(PhieuNhapHang pnh: hdtpDTO.docDanhSachPhieuNhapHang()){
            if(pnh.getMaPhieuNhapHang().compareTo(ctnh.getMaPhieuNhapHang())==0){
                mpnh = true;
            }
        }
        if(mmh==false){
            JOptionPane.showMessageDialog(null, "Mã mặt hàng không tồn tại");
            return false;
        }
        if(mpnh==false){
            JOptionPane.showMessageDialog(null, "Mã phiếu nhập hàng chưa tồn tại");
            return false;
        }
        ctnhDAO.themChiTietNhapHang(ctnh);
        ctnhDTO.themChiTietNhapHang(ctnh);
        return true;
    }
    
    public boolean suaChiTietNhapHang(ChiTietNhapHangDTO ctnhDTO,ChiTietNhapHang ctnh){
        int row = -1;

        for(int i=0;i<ctnhDTO.docDanhSachChiTietNhapHang().size();i++){
            if(ctnh.getMaMatHang().compareTo(ctnhDTO.docDanhSachChiTietNhapHang().get(i).getMaMatHang())==0 &&
                    ctnh.getMaPhieuNhapHang().compareTo(ctnhDTO.docDanhSachChiTietNhapHang().get(i).getMaPhieuNhapHang())==0){
                row = i;
                break;
            }
        }
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã mặt hàng và mã phiếu nhập hàng");
            return false;
        }

        if(ctnh.getDonGia()==ctnhDTO.docDanhSachChiTietNhapHang().get(row).getDonGia() &&
                ctnh.getSoLuong()==ctnhDTO.docDanhSachChiTietNhapHang().get(row).getSoLuong() &&
                ctnh.getThanhTien()==ctnhDTO.docDanhSachChiTietNhapHang().get(row).getThanhTien()){
            JOptionPane.showMessageDialog(null, "Không có gì để sửa");
            return false;
        }
        int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn sửa");
            if(chon==JOptionPane.YES_OPTION){
                ctnhDAO.suaChiTietNhapHang(ctnh);
                ctnhDTO.suaChiTietNhapHang(ctnh, row);
                return true;
            }else{
                return false;
        }
    }
    
    public boolean xoaChiTietNhapHang(ChiTietNhapHangDTO ctnhDTO,ChiTietNhapHang ctnh){
        int row = -1;

        for(int i=0;i<ctnhDTO.docDanhSachChiTietNhapHang().size();i++){
            if(ctnh.getMaMatHang().compareTo(ctnhDTO.docDanhSachChiTietNhapHang().get(i).getMaMatHang())==0 &&
                    ctnh.getMaPhieuNhapHang().compareTo(ctnhDTO.docDanhSachChiTietNhapHang().get(i).getMaPhieuNhapHang())==0){
                row = i;
                break;
            }
        }
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã mặt hàng và mã phiếu nhập hàng");
            return false;
        }

        int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa");
            if(chon==JOptionPane.YES_OPTION){
                ctnhDAO.xoaChiTietNhapHang(ctnh.getMaPhieuNhapHang(), ctnh.getMaMatHang());
                ctnhDTO.xoaChiTietNhapHang(row);
                return true;
            }else{
                return false;
        }
    }
    
    public boolean xoaChiTietNhapHangTuPhieuNhapHang(ChiTietNhapHangDTO ctnhDTO,ChiTietNhapHang ctnh, int vitri){
        ctnhDAO.xoaChiTietNhapHang(ctnh.getMaPhieuNhapHang(), ctnh.getMaMatHang());
        ctnhDTO.xoaChiTietNhapHang(vitri);
        return true;
    }
}
