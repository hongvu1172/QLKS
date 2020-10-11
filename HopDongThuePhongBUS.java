/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlks.BUS;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import qlks.DAO.HopDongThuePhongDAO;
import qlks.DTO.HopDongThuePhong;
import qlks.DTO.HopDongThuePhongDTO;
import qlks.DTO.KhachHang;
import qlks.DTO.KhachHangDTO;
import qlks.DTO.NhanVien;
import qlks.DTO.NhanVienDTO;
import qlks.DTO.Phong;
import qlks.DTO.PhongDTO;

/**
 *
 * @author admin
 */
public class HopDongThuePhongBUS {
    private HopDongThuePhongDAO hdtpDAO;
    
    public HopDongThuePhongBUS(){
        hdtpDAO = new HopDongThuePhongDAO();
    }
    
    public ArrayList<HopDongThuePhong> docDanhSachHopDongThuePhong(){
        return hdtpDAO.docDanhSachHopDongThuePhong();
    }
    
    public boolean kiemTraKhoaNgoai(HopDongThuePhong hdtp){
        NhanVienDTO nvDTO = new NhanVienDTO();
        boolean mnv = false;
        for(NhanVien nv: nvDTO.docDanhSachNhanVien()){
            if(nv.getMaNhanVien().compareTo(hdtp.getMaNhanVien())==0){
                mnv = true;
            }
        }
        if(mnv==false){
            JOptionPane.showMessageDialog(null, "Mã nhân viên chưa tồn tại");
            return false;
        }
        
        KhachHangDTO khDTO = new KhachHangDTO();
        boolean mkh = false;
        for(KhachHang kh: khDTO.docDanhSachKhachHang()){
            if(kh.getMaKhachHang().compareTo(hdtp.getMaKhachHang())==0){
                mkh = true;
            }
        }
        if(mkh==false){
            JOptionPane.showMessageDialog(null, "Mã khách hàng chưa tồn tại");
            return false;
        }
        
        PhongDTO pDTO = new PhongDTO();
        boolean mp = false;
        for(Phong p: pDTO.docDanhSachPhong()){
            if(p.getMaPhong().compareTo(hdtp.getMaPhong())==0){
                mp = true;
            }
        }
        if(mp==false){
            JOptionPane.showMessageDialog(null, "Hãy kiểm tra mã phòng");
            return false;
        }
        return true;
    }
    
    public boolean themHopDongThuePhong(HopDongThuePhongDTO hdtpDTO, HopDongThuePhong hdtp){
        for(int i=0;i<hdtpDTO.docDanhSachHopDongThuePhong().size();i++){
            HopDongThuePhong hdtpt = hdtpDTO.docDanhSachHopDongThuePhong().get(i);
            if(hdtpt.getSoHopDongThuePhong().compareTo(hdtp.getSoHopDongThuePhong())==0){
                if(hdtpt.isXoa()){
                    if(JOptionPane.showConfirmDialog(null, "Phát hiện số hợp đồng cũ bạn muốn ghi đè lên hợp đồng này")
                            ==JOptionPane.YES_OPTION){
                        if(kiemTraKhoaNgoai(hdtp)==false){
                            return false;
                        }
                        hdtpDAO.suaHopDongThuePhong(hdtp);
                        hdtpDTO.suaHopDongThuePhong(hdtp, i);
                        return true;
                    }
                    return false;
                }
                JOptionPane.showMessageDialog(null, "Trùng số hợp đồng.");
                return false;
            }
        }
        
        if(kiemTraKhoaNgoai(hdtp)==false){
            return false;
        }
        
        PhongBUS pBUS = new PhongBUS();
        for(Phong pt: pBUS.docDanhSachPhong()){
            if(hdtp.getMaPhong().compareTo(pt.getMaPhong())==0&&pt.getTinhTrang().compareTo("Trống")==0){
                hdtpDAO.themHopDongThuePhong(hdtp);
                hdtpDTO.themHopDongThuePhong(hdtp);
                return true;
            }else{
                if(hdtp.getMaPhong().compareTo(pt.getMaPhong())==0&&pt.getTinhTrang().compareTo("Bảo trì")==0){
                    JOptionPane.showMessageDialog(null, "Phòng đang bảo trì vui lòng chọn phòng khác");
                    return false;
                }else{
                    if(hdtp.getMaPhong().compareTo(pt.getMaPhong())==0&&pt.getTinhTrang().compareTo("Đã dùng")==0){
                        JOptionPane.showMessageDialog(null, "Phòng này đã có người sử dụng vui lòng chọn phòng khác");
                        return false;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean suaHopDongThuePhongTuHoaDon(HopDongThuePhong hdtp){
        hdtpDAO.suaHopDongThuePhong(hdtp);
        return true;
    }
    
    public boolean suaHopDongThuePhong(HopDongThuePhongDTO hdtpDTO,HopDongThuePhong hdtp){
        int row = -1;

        for(int i=0;i<hdtpDTO.docDanhSachHopDongThuePhong().size();i++){
            if(hdtp.getSoHopDongThuePhong().compareTo(hdtpDTO.docDanhSachHopDongThuePhong().get(i).getSoHopDongThuePhong())==0){
                row = i;
                break;
            }
        }
        if(row == -1 || hdtpDTO.docDanhSachHopDongThuePhong().get(row).isXoa()){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại số hợp đồng");
            return false;
        }
        
        if(hdtp.getMaNhanVien().compareTo(hdtpDTO.docDanhSachHopDongThuePhong().get(row).getMaNhanVien())==0 &&
                hdtp.getMaPhong().compareTo(hdtpDTO.docDanhSachHopDongThuePhong().get(row).getMaPhong())==0 &&
                hdtp.getMaKhachHang().compareTo(hdtpDTO.docDanhSachHopDongThuePhong().get(row).getMaKhachHang())==0 &&
                hdtp.getTienPhong()==hdtpDTO.docDanhSachHopDongThuePhong().get(row).getTienPhong() &&
                hdtp.getNgayThue().compareTo(hdtpDTO.docDanhSachHopDongThuePhong().get(row).getNgayThue())==0 &&
                hdtp.getNgayTra().compareTo(hdtpDTO.docDanhSachHopDongThuePhong().get(row).getNgayTra())==0 &&
                hdtp.getTrangThai().compareTo(hdtpDTO.docDanhSachHopDongThuePhong().get(row).getTrangThai())==0 &&
                hdtp.getGhiChu().compareTo(hdtpDTO.docDanhSachHopDongThuePhong().get(row).getGhiChu())==0){
            JOptionPane.showMessageDialog(null, "Không có gì để sửa");
            return false;
        }
        
        if(kiemTraKhoaNgoai(hdtp)==false){
            return false;
        }
        
        int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn sửa hợp đồng này");
            if(chon==JOptionPane.YES_OPTION){
                hdtpDAO.suaHopDongThuePhong(hdtp);
                hdtpDTO.suaHopDongThuePhong(hdtp, row);
                return true;
            }else{
                return false;
        }
    }
    
    public boolean xoaHopDongThuePhong(HopDongThuePhongDTO hdtpDTO, String shdtp){
        int row = -1;
        for(int i=0;i<hdtpDTO.docDanhSachHopDongThuePhong().size();i++){
            if(shdtp.compareTo(hdtpDTO.docDanhSachHopDongThuePhong().get(i).getSoHopDongThuePhong())==0){
                row = i;
                break;
            }
        }
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại số hợp đồng");
            return false;
        }
        
        int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa hợp đồng này");
        if(chon==JOptionPane.YES_OPTION){
            hdtpDAO.xoaHopDongThuePhong(shdtp);
            hdtpDTO.xoaHopDongThuePhong(shdtp, row);
            return true;
        }else{
            return false;
        }
    }
}
