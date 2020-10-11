/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlks.BUS;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import qlks.DAO.DanhMucKhuyenMaiDAO;
import qlks.DTO.DanhMucKhuyenMai;
import qlks.DTO.DanhMucKhuyenMaiDTO;
import qlks.DTO.HopDongThuePhong;
import qlks.DTO.HopDongThuePhongDTO;
import qlks.DTO.KhuyenMai;
import qlks.DTO.KhuyenMaiDTO;

/**
 *
 * @author admin
 */
public class DanhMucKhuyenMaiBUS {
    private DanhMucKhuyenMaiDAO dmkmDAO;
    
    public DanhMucKhuyenMaiBUS(){
        dmkmDAO = new DanhMucKhuyenMaiDAO();
    }
    
    public ArrayList<DanhMucKhuyenMai> docDanhSachDanhMucKhuyenMai(){
        return dmkmDAO.docDanhSachDanhMucKhuyenMai();
    }
    
    public boolean themDanhMucKhuyenMai(DanhMucKhuyenMaiDTO dmkmDTO, DanhMucKhuyenMai dmkm){
        for(DanhMucKhuyenMai dmkmt: dmkmDTO.docDanhSachDanhMucKhuyenMai()){
            if(dmkmt.getSoHopDongThuePhong().compareTo(dmkm.getSoHopDongThuePhong())==0 &&
                    dmkmt.getMaChuongTrinhKhuyenMai().compareTo(dmkm.getMaChuongTrinhKhuyenMai())==0){
                JOptionPane.showMessageDialog(null, "Không thể thêm cùng một mã khuyến mãi trên cùng một hợp đồng");
                return false;
            }
        }
        KhuyenMaiDTO kmDTO = new KhuyenMaiDTO();
        HopDongThuePhongDTO hdtpDTO = new HopDongThuePhongDTO();
        boolean mkm = false;
        boolean shdtp = false;
        for(KhuyenMai km: kmDTO.docDanhSachKhuyenMai()){
            if(km.getMaChuongTrinhKhuyenMai().compareTo(dmkm.getMaChuongTrinhKhuyenMai())==0){
                mkm = true;
            }
        }
        for(HopDongThuePhong hdtp: hdtpDTO.docDanhSachHopDongThuePhong()){
            if(hdtp.getSoHopDongThuePhong().compareTo(dmkm.getSoHopDongThuePhong())==0){
                shdtp = true;
            }
        }
        if(mkm==false){
            JOptionPane.showMessageDialog(null, "Mã khuyến mãi không tồn tại vui lòng chọ mã khác");
            return false;
        }
        if(shdtp==false){
            JOptionPane.showMessageDialog(null, "Số hợp đồng chưa tồn tại");
            return false;
        }
        dmkmDAO.themDanhMucKhuyenMai(dmkm);
        dmkmDTO.themDanhMucKhuyenMai(dmkm);
        return true;
    }
        
    public boolean suaDanhMucKhuyenMai(DanhMucKhuyenMaiDTO dmkmDTO,DanhMucKhuyenMai dmkm){
        int row = -1;

        for(int i=0;i<dmkmDTO.docDanhSachDanhMucKhuyenMai().size();i++){
            if(dmkm.getMaChuongTrinhKhuyenMai().compareTo(dmkmDTO.docDanhSachDanhMucKhuyenMai().get(i).getMaChuongTrinhKhuyenMai())==0 &&
                    dmkm.getSoHopDongThuePhong().compareTo(dmkmDTO.docDanhSachDanhMucKhuyenMai().get(i).getSoHopDongThuePhong())==0){
                row = i;
                break;
            }
        }
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã khuyến mãi và số hợp đồng");
            return false;
        }

        if(dmkm.getLoaiKhuyenMai().compareTo(dmkmDTO.docDanhSachDanhMucKhuyenMai().get(row).getLoaiKhuyenMai())==0 &&
                dmkm.getNgayKhuyenMai().compareTo(dmkmDTO.docDanhSachDanhMucKhuyenMai().get(row).getNgayKhuyenMai())==0 &&
                dmkm.getNoiDunngKhuyenMai().compareTo(dmkmDTO.docDanhSachDanhMucKhuyenMai().get(row).getNoiDunngKhuyenMai())==0 &&
                dmkm.getGiamGia()==dmkmDTO.docDanhSachDanhMucKhuyenMai().get(row).getGiamGia()){
            JOptionPane.showMessageDialog(null, "Không có gì để sửa");
            return false;
        }
        int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn sửa");
            if(chon==JOptionPane.YES_OPTION){
                dmkmDAO.suaDanhMucKhuyenMai(dmkm);
                dmkmDTO.suaDanhMucKhuyenMai(dmkm, row);
                return true;
            }else{
                return false;
        }
    }
    
    public boolean xoaDanhMucKhuyenMai(DanhMucKhuyenMaiDTO dmkmDTO, DanhMucKhuyenMai dmkm){
        int row = -1;

        for(int i=0;i<dmkmDTO.docDanhSachDanhMucKhuyenMai().size();i++){
            if(dmkm.getMaChuongTrinhKhuyenMai().compareTo(dmkmDTO.docDanhSachDanhMucKhuyenMai().get(i).getMaChuongTrinhKhuyenMai())==0 &&
                    dmkm.getSoHopDongThuePhong().compareTo(dmkmDTO.docDanhSachDanhMucKhuyenMai().get(i).getSoHopDongThuePhong())==0){
                row = i;
                break;
            }
        }
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã khuyến mãi và số hợp đồng");
            return false;
        }
        
        if(JOptionPane.showConfirmDialog(null, "Bạn muốn xóa")==JOptionPane.YES_OPTION){
            dmkmDAO.xoaDanhMucKhuyenMai(dmkm.getSoHopDongThuePhong(), dmkm.getMaChuongTrinhKhuyenMai());
            dmkmDTO.xoaDanhMucKhuyenMai(row);
            return true;
        }
        return false;
    }
}
