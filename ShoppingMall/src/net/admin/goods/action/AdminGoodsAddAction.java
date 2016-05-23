package net.admin.goods.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import net.admin.goods.db.*;
public class AdminGoodsAddAction implements Action {
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) {
		ActionForward forward = new ActionForward();
		AdminGoodsDAO agoodsdao= new AdminGoodsDAO();
		GoodsBean agb = new GoodsBean();
		String realPath = "";
		String savePath = "upload";
		int maxSize = 5 * 1024 * 1024;
		realPath = request.getRealPath(savePath);
		System.out.println(realPath+"PATHPATHPATHPATH");
		//���� ���� �������� ���ε� ��θ� ��� ��
		List savefiles=new ArrayList();
		try {
			MultipartRequest multi = null;
			multi = new MultipartRequest(request, realPath, maxSize, "euc-kr",
					new DefaultFileRenamePolicy());
			//
			Enumeration files=multi.getFileNames();
			while(files.hasMoreElements()){
				String name=(String)files.nextElement();
				//file input box �� �̸����� �ϳ��� ������
				if(files.hasMoreElements()){
					//���� ������ �̸� ���� �̸��� �� ������
					//��, ���� enumeration���� ������ �̸��� ������ ��Ұ� �ƴϸ�
					savefiles.add(multi.getFilesystemName(name)+",");
				}else{
					savefiles.add(multi.getFilesystemName(name));
				}
				//������ ������ �ƴϸ� ���ϸ� �ڿ� , �� �ٿ��� �߰��ϰ�
				//������ �����̸� , �� ������ �ʰ� �߰�
			}
			StringBuffer fl=new StringBuffer();
			for(int i=0;i<savefiles.size();i++){
				fl.append(savefiles.get(i));	
			}
			System.out.println(savefiles.size()+"savefiles sizesizesize");
			//main.jpg,image1-1.jpg,image1-2.jpg,image1-3.jpg
			agb.setGOODS_CATEGORY(multi.getParameter("goods_category"));
			agb.setGOODS_NAME(multi.getParameter("goods_name"));
			agb.setGOODS_CONTENT(multi.getParameter("goods_content"));
			agb.setGOODS_SIZE(multi.getParameter("goods_size"));
			agb.setGOODS_COLOR(multi.getParameter("goods_color"));
			agb.setGOODS_AMOUNT(
					Integer.parseInt(multi.getParameter("goods_amount")));
			agb.setGOODS_PRICE(
					Integer.parseInt(multi.getParameter("goods_price")));
			agb.setGOODS_IMAGE(fl.toString());
			agb.setGOODS_BEST(
					Integer.parseInt(multi.getParameter("goods_best")));
			
			int result = agoodsdao.insertGoods(agb);
			//MultiPartRequest ��ü ������ �� �̹� ���ε� ó�� �Ǿ���
			//���ε� �� ���Ͽ� ���õ� ���� �Ӽ����� TO ��ü�� ��Ƽ� �Ķ���ͷ� �����ϸ鼭
			//DB�� �Է� �۾��� ��
			if (result <= 0){
				response.setContentType("text/html;charset=euc-kr");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('��Ͻ���')");
				out.println("history.back()");
				out.println("</script>");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		forward.setRedirect(true);
		forward.setPath("GoodsList.ag");
		return forward;
	}
}