/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlks.BUS;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import qlks.DAO.PhieuNhapHangDAO;
import qlks.DTO.ChiTietNhapHang;
import qlks.DTO.ChiTietNhapHangDTO;
import qlks.DTO.NhanVien;
import qlks.DTO.NhanVienDTO;
import qlks.DTO.PhieuNhapHang;
import qlks.DTO.PhieuNhapHangDTO;

/**
 *
 * @author admin
 */
public class PhieuNhapHangBUS {
    private PhieuNhapHangDAO pnhDAO;
    
    public PhieuNhapHangBUS(){
        pnhDAO = new PhieuNhapHangDAO();
    }
    
    public ArrayList<PhieuNhapHang> docDanhSachPhieuNhapHang(){
        return pnhDAO.docDanhSachPhieuNhapHang();
    }
    
    public ArrayList<PhieuNhapHang> danhSachNgayNhap(String tu, String den){
        return pnhDAO.danhSachNgayNhap(tu, den);
    }
    
    public boolean themPhieuNhapHang(PhieuNhapHangDTO pnhDTO, PhieuNhapHang pnh){
        for(PhieuNhapHang pnht: pnhDTO.docDanhSachPhieuNhapHang()){
            if(pnht.getMaPhieuNhapHang().compareTo(pnh.getMaPhieuNhapHang())==0){
                JOptionPane.showMessageDialog(null, "Trùng mã mặt hàng.");
                return false;
            }
        }
        
        boolean mnv = false;
        NhanVienDTO nvDTO = new NhanVienDTO();
        for(NhanVien nv: nvDTO.docDanhSachNhanVien()){
            if(nv.getMaNhanVien().compareTo(pnh.getMaNhanVien())==0){
                mnv = true;
                break;
            }
        }
        if(mnv == false){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã nhân viên");
            return false;
        }
        
        pnhDAO.themPhieuNhapHang(pnh);
        pnhDTO.themPhieuNhapHang(pnh);
        return true;
    }
    
    public boolean suaPhieuNhapHang(PhieuNhapHangDTO pnhDTO,PhieuNhapHang pnh){
        int row = -1;

        for(int i=0;i<pnhDTO.docDanhSachPhieuNhapHang().size();i++){
            if(pnh.getMaPhieuNhapHang().compareTo(pnhDTO.docDanhSachPhieuNhapHang().get(i).getMaPhieuNhapHang())==0){
                row = i;
                break;
            }
        }
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã phiếu nhập hàng");
            return false;
        }

        if(pnh.getMaNhanVien().compareTo(pnhDTO.docDanhSachPhieuNhapHang().get(row).getMaNhanVien())==0 &&
                pnh.getNgayNhapHang().compareTo(pnhDTO.docDanhSachPhieuNhapHang().get(row).getNgayNhapHang())==0 &&
                pnh.getNhaCungCap().compareTo(pnhDTO.docDanhSachPhieuNhapHang().get(row).getNhaCungCap())==0 &&
                pnh.getTongTien()==pnhDTO.docDanhSachPhieuNhapHang().get(row).getTongTien()){
            JOptionPane.showMessageDialog(null, "Không có gì để sửa ở phiếu nhập hàng");
            return false;
        }
        
        boolean mnv = false;
        NhanVienDTO nvDTO = new NhanVienDTO();
        for(NhanVien nv: nvDTO.docDanhSachNhanVien()){
            if(nv.getMaNhanVien().compareTo(pnh.getMaNhanVien())==0){
                mnv = true;
                break;
            }
        }
        if(mnv == false){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã nhân viên");
            return false;
        }
        
        int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn sửa phiếu nhập hàng này");
            if(chon==JOptionPane.YES_OPTION){
                pnhDAO.suaPhieuNhapHang(pnh);
                pnhDTO.suaPhieuNhapHang(pnh, row);
                return true;
            }else{
                return false;
        }
    }
    
    public boolean xoaPhieuNhapHang(PhieuNhapHangDTO pnhDTO,PhieuNhapHang pnh){
        int row = -1;

        for(int i=0;i<pnhDTO.docDanhSachPhieuNhapHang().size();i++){
            if(pnh.getMaPhieuNhapHang().compareTo(pnhDTO.docDanhSachPhieuNhapHang().get(i).getMaPhieuNhapHang())==0){
                row = i;
                break;
            }
        }
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã phiếu nhập hàng");
            return false;
        }
        
        int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa phiếu nhập hàng này");
            if(chon==JOptionPane.YES_OPTION){
                ChiTietNhapHangBUS ctnhBUS = new ChiTietNhapHangBUS();
                ChiTietNhapHangDTO ctnhDTO = new ChiTietNhapHangDTO();
                for(int i=0;i<ctnhDTO.docDanhSachChiTietNhapHang().size();i++){
                    if(ctnhDTO.docDanhSachChiTietNhapHang().get(i).getMaPhieuNhapHang().compareTo(pnh.getMaPhieuNhapHang())==0){
                        ctnhBUS.xoaChiTietNhapHangTuPhieuNhapHang(ctnhDTO,ctnhDTO.docDanhSachChiTietNhapHang().get(i),i);
                    }
                }
                
                pnhDAO.xoaPhieuNhapHang(pnh.getMaPhieuNhapHang());
                pnhDTO.xoaPhieuNhapHang(row);
                return true;
            }else{
                return false;
        }
    }
    
    public void suaTongTien(PhieuNhapHang pnh){
        pnhDAO.suaPhieuNhapHang(pnh);
    }
}
