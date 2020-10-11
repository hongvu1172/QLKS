
package qlks.BUS;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import qlks.DAO.NhanVienDAO;
import qlks.DTO.NhanVien;
import qlks.DTO.NhanVienDTO;

public class NhanVienBUS {
    private NhanVienDAO nvDAO;
    
    public NhanVienBUS(){
        nvDAO = new NhanVienDAO();
    }
    
    //Lay danh sach nhan vien tu DAO
    public ArrayList<NhanVien> docDanhSachNhanVien(){
        return nvDAO.docDanhSachNhanVien();
    }
    
    //Nhan vien DTO va nhan vien can them
    public boolean themNhanVien(NhanVienDTO nvDTO, NhanVien nv){
        //Kiem tra ma nhan vien da co hay chua
        for(int i=0;i<nvDTO.docDanhSachNhanVien().size();i++){
            NhanVien nvt = nvDTO.docDanhSachNhanVien().get(i);
            //Neu tim thay nhan vien da co trong DTO thi vao
            if(nvt.getMaNhanVien().compareTo(nv.getMaNhanVien())==0){
                //kiem tra xem co danh dau la xoa ko
                if(nvt.isXoa()){
                    //Hoi nguoi dung muon ghi de len nhan vien danh dau la Xoa
                    if(JOptionPane.showConfirmDialog(null, "Phát hiện mã nhân viên cũ bạn muốn ghi đè lên nhân viên này")
                            ==JOptionPane.YES_OPTION){
                        nvDAO.suaNhanVien(nv);
                        nvDTO.suaNhanVien(nv, i);
                        return true;
                    }
                    return false;
                }
                //
                JOptionPane.showMessageDialog(null, "Mã nhân viên đã tồn tại.");
                return false;
            }
        }
        //them nhan vien moi vao SQL va nhan vien DTO
        nvDAO.themNhanVien(nv);
        nvDTO.themNhanVien(nv);
        return true;
    }
    
    //row = vi tri can sua trong svDTO
    public boolean suaNhanVien(NhanVienDTO nvDTO,NhanVien nv){
        int row = -1;
        //Kiem tra ma nhan vien co ton tai trong nvDTO
        for(int i=0;i<nvDTO.docDanhSachNhanVien().size();i++){
            if(nv.getMaNhanVien().compareTo(nvDTO.docDanhSachNhanVien().get(i).getMaNhanVien())==0){
                row = i;
                break;
            }
        }
        //Neu row = -1 thi da ton tai trong DTO
        //Hoac nhan vien do bi danh dau la xoa thi ko can sua .isXoa tra ve true hoac false
        if(row == -1 || nvDTO.docDanhSachNhanVien().get(row).isXoa()){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã nhân viên");
            return false;
        }
        //Kiem tra nhan vien co thay doi khong, neu ko thi ko can sua
        if(nv.getMaNhanVien().compareTo(nvDTO.docDanhSachNhanVien().get(row).getMaNhanVien())==0 &&
                nv.getHo().compareTo(nvDTO.docDanhSachNhanVien().get(row).getHo())==0 &&
                nv.getTen().compareTo(nvDTO.docDanhSachNhanVien().get(row).getTen())==0 &&
                nv.getNgaySinh().compareTo(nvDTO.docDanhSachNhanVien().get(row).getNgaySinh())==0 &&
                nv.getGioiTinh().compareTo(nvDTO.docDanhSachNhanVien().get(row).getGioiTinh())==0 &&
                nv.getDiaChi().compareTo(nvDTO.docDanhSachNhanVien().get(row).getDiaChi())==0 &&
                nv.getCMMD().compareTo(nvDTO.docDanhSachNhanVien().get(row).getCMMD())==0 &&
                nv.getMaNhanVien().compareTo(nvDTO.docDanhSachNhanVien().get(row).getMaNhanVien())==0 &&
                nv.getDienThoai().compareTo(nvDTO.docDanhSachNhanVien().get(row).getDienThoai())==0 &&
                nv.getLuongThang()==nvDTO.docDanhSachNhanVien().get(row).getLuongThang()){
            JOptionPane.showMessageDialog(null, "Không có gì để sửa");
            return false;
        }
        // nguoi dung va sua trong DAO va DTO
        int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn sửa nhân viên này");
            if(chon==JOptionPane.YES_OPTION){
                nvDAO.suaNhanVien(nv);
                nvDTO.suaNhanVien(nv, row);
                return true;
            }else{
                return false;
        }
    }
    
    public boolean xoaNhanVien(NhanVienDTO nvDTO, String mnv){
        int row = -1;
        //Kiem tra ma nhan vien co ton tai trong nvDTO
        for(int i=0;i<nvDTO.docDanhSachNhanVien().size();i++){
            if(mnv.compareTo(nvDTO.docDanhSachNhanVien().get(i).getMaNhanVien())==0){
                row = i;
                break;
            }
        }
        
        //Neu ko ton tai thi thoi
        if(row == -1){
            JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại mã nhân viên");
            return false;
        }
        
        //Xoa trong DAO va DTO chi danh dau la xoa chu ko xoa xem trong nhan vien DAO va DTO
        int chon = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa nhân viên này");
            if(chon==JOptionPane.YES_OPTION){
                nvDAO.xoaNhanVien(mnv);
                nvDTO.xoaNhanVien(row);
                return true;
            }else{
                return false;
        }
    }
    
    public boolean doiMatKhauNhanVien(String mnv, String mkm){
        for(NhanVien nv: new NhanVienDTO().docDanhSachNhanVien()){
            if(nv.getMaNhanVien().compareTo(mnv)==0 && nv.getMatKhau().compareTo(mkm)==0){
                JOptionPane.showMessageDialog(null, "Vui lòng chọn mật khẩu khác.");
                return false;
            }
        }
        
        int chon = JOptionPane.showConfirmDialog(null, "Xác nhận đổi");
            if(chon==JOptionPane.YES_OPTION){
                nvDAO.doiMatKhauNhanVien(mnv,mkm);
                return true;
            }else{
                return false;
        }
    }
}
