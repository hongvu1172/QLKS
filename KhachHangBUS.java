
package qlks.BUS;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import qlks.DAO.KhachHangDAO;
import qlks.DTO.KhachHang;
import qlks.DTO.KhachHangDTO;


public class KhachHangBUS {
    private KhachHangDAO khDAO;
    
    public KhachHangBUS(){
        khDAO = new KhachHangDAO();
    }
    
    public ArrayList<KhachHang> docDanhSachKhachHang(){
        return khDAO.docDanhSachKhachHang();
    }
    
    public boolean themKhachHang(KhachHangDTO khDTO, KhachHang kh){
        
        for(int i=0;i<khDTO.docDanhSachKhachHang().size();i++){
            KhachHang kht = khDTO.docDanhSachKhachHang().get(i);
            if(kht.getMaKhachHang().compareTo(kh.getMaKhachHang())==0){
                if(kht.isXoa()){
                    if(JOptionPane.showConfirmDialog(null, "Phát hiện mã khách hàng cũ bạn muốn ghi đè lên khách hàng này")
                            ==JOptionPane.YES_OPTION){
                        khDAO.suaKhachHang(kh);
                        khDTO.suaKhachHang(kh, i);
                        return true;
                    }
                    return false;
                }
                JOptionPane.showMessageDialog(null, "Mã khách hàng đã tồn tại.");
                return false;
            }
        }

        khDAO.themKhachHang(kh);
        khDTO.themKhachHang(kh);
        return true;
    }
    
    public boolean suaKhachHang(KhachHangDTO khDTO,KhachHang kh){
        int row = -1;

        for(int i=0;i<khDTO.docDanhSachKhachHang().size();i++){
            if(kh.getMaKhachHang().compareTo(khDTO.docDanhSachKhachHang().get(i).getMaKhachHang())==0){
                row = i;
                break;
            }
        }
        if(row == -1 || khDTO.docDanhSachKhachHang().get(row).isXoa()){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã khách hàng");
            return false;
        }

        if(kh.getMaKhachHang().compareTo(khDTO.docDanhSachKhachHang().get(row).getMaKhachHang())==0 &&
                kh.getHo().compareTo(khDTO.docDanhSachKhachHang().get(row).getHo())==0 &&
                kh.getTen().compareTo(khDTO.docDanhSachKhachHang().get(row).getTen())==0 &&
                kh.getGioiTinh().compareTo(khDTO.docDanhSachKhachHang().get(row).getGioiTinh())==0 &&
                kh.getDiaChi().compareTo(khDTO.docDanhSachKhachHang().get(row).getDiaChi())==0 &&
                kh.getCMND().compareTo(khDTO.docDanhSachKhachHang().get(row).getCMND())==0 &&
                kh.getDienThoai().compareTo(khDTO.docDanhSachKhachHang().get(row).getDienThoai())==0){
            JOptionPane.showMessageDialog(null, "Không có gì để sửa");
            return false;
        }
        int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn sửa khách hàng này");
            if(chon==JOptionPane.YES_OPTION){
                khDAO.suaKhachHang(kh);
                khDTO.suaKhachHang(kh, row);
                return true;
            }else{
                return false;
        }
    }
    
    public boolean xoaKhachHang(KhachHangDTO khDTO, String mkh){
        int row = -1;
        for(int i=0;i<khDTO.docDanhSachKhachHang().size();i++){
            if(mkh.compareTo(khDTO.docDanhSachKhachHang().get(i).getMaKhachHang())==0){
                row = i;
                break;
            }
        }
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã khách hàng");
            return false;
        }
        
        int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa khách hàng này");
        if(chon==JOptionPane.YES_OPTION){
            khDAO.xoaKhachHang(mkh);
            khDTO.xoaKhachHang(row);
            return true;
        }else{
            return false;
        }
    }
}
