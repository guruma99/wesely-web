package com.wesely.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wesely.dao.CommunityDAO;
import com.wesely.dao.MemberDAO;
import com.wesely.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("memberService")
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberDAO memberDAO;
	@Autowired
	CommunityDAO communityDAO;

	@Override
	public void insert(MemberVO memberVO) {
		log.info("{}의 insert호출 : {}", this.getClass().getName(), memberVO);
		memberDAO.insert(memberVO);
	}

	@Override
	public boolean delete(MemberVO memberVO) {
		boolean result = false;
		log.info("{}의 delete호출 : {}", this.getClass().getName(), memberVO);
		MemberVO dbVO = memberDAO.selectByUserid(memberVO.getUserid());
		if(dbVO != null) {
			// 입력한 비밀번호가 db정보와 일치한다면
			if(dbVO.getPassword().equals(memberVO.getPassword())) {
				communityDAO.deleteNickname(dbVO.getNickname());
				memberDAO.delete(memberVO);	// 탈퇴
				result = true;
			}
		}
		return result;
	}

	@Override
	public MemberVO login(MemberVO vo) {
		log.info("{}의 login호출 : {}", this.getClass().getName(), vo);
		MemberVO memberVO = null;
		try {
			// 넘어온 아이디가 존재하는지 판단
			MemberVO mvo = memberDAO.selectByUserid(vo.getUserid());
			if (mvo != null) { // 지정 아이디의 회원이 있다면
				if (mvo.getPassword().equals(vo.getPassword())) {
					memberVO = mvo;
				} else {
				}
			} else {
				// 아이디가 없다
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("login({}) 리턴", vo, memberVO);
		return memberVO;
	}

	@Override
	public void logout() {
		log.info("{}의 logout호출", this.getClass().getName());

	}

	@Override
	public List<MemberVO> selectList() {
		List<MemberVO> list = null;
		log.info("{}의 selectList 호출", this.getClass().getName());

		log.info("{}의 selectList 리턴 : {}", this.getClass().getName(), list);
		return list;
	}

	@Override
	public void emailCheck(String uuid, String userid) {
		log.info("{}의 emailCheck 호출 : {}", this.getClass().getName(), userid + "," + uuid);

	}

	@Override
	public int idCheck(String userid) {
		log.info("{}의 idCheck 호출 : {}", this.getClass().getName(), userid);
		int idcount = memberDAO.selectCountByUserid(userid);
		log.info("{}의 idCheck 리턴 : {}", this.getClass().getName(), idcount);
		return idcount;
	}

	@Override
	public int nicknameCheck(String nickname) {
		log.info("{}의 nicknameCheck 호출 : {}", this.getClass().getName(), nickname);
		int nickcount = memberDAO.selectCountByNickname(nickname);
		log.info("{}의 nicknameCheck 리턴 : {}", this.getClass().getName(), nickcount);
		return nickcount;
	}

	@Override
	public int phoneCheck(String phone) {
		log.info("{}의 phoneCheck 호출 : {}", this.getClass().getName(), phone);
		int phonecount = memberDAO.selectCountByPhone(phone);
		log.info("{}의 phoneCheck 리턴 : {}", this.getClass().getName(), phonecount);
		return phonecount;
	}

	@Override
	public MemberVO findUserId(MemberVO VO) {
		log.info("findUserId({}) 호출", VO);
		MemberVO memberVO = null;

		try {
			// 전화번호로 찾기
			MemberVO dbVO = memberDAO.selectByPhone(VO.getPhone());
			if (dbVO != null) { // 전화번호가 있고
				// 이름도 같으면
				if (dbVO.getUsername().equals(VO.getUsername())) {
					memberVO = dbVO;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("findUserId({}) 리턴 : {}", VO, memberVO);
		return memberVO;
	}


	@Override
	public MemberVO findPassword(MemberVO memberVO) {
		log.info("{}의 findPassword 호출 : {}", this.getClass().getName(), memberVO);
		MemberVO memberVO2 = memberDAO.selectByPassword(memberVO.getPassword());

		log.info("{}의 findPassword 리턴 : {}", this.getClass().getName(), memberVO2);
		return memberVO2;
	}


	public boolean updateNickname(MemberVO memberVO) {
		boolean result = false;
		log.info("{}의 updateNickname호출 : {}", this.getClass().getName(), memberVO);
		MemberVO dbVO = memberDAO.selectByUserid(memberVO.getUserid());

		if (dbVO != null) {
			// 게시판의 정보를 변경
			HashMap<String, String> map = new HashMap<>();
			map.put("newNickname", memberVO.getNickname());
			map.put("oldNickname", dbVO.getNickname());
			communityDAO.updateNickname(map);
			// 회원정보변경
			result = true;
		}
		return result;
	}

	@Override
	public boolean updatePassword(MemberVO memberVO, String newPassword) {
		boolean result = false;
		log.info("{}의 updatePassword 호출 : {}", this.getClass().getName(), memberVO);
		MemberVO dbVO = memberDAO.selectByUserid(memberVO.getUserid());
		if (dbVO != null) {
			// 입력한 비밀번호가 db정보와 일치한다면
			if (dbVO.getPassword().equals(memberVO.getPassword())) {
				// 비밀번호를 바꾼다
				memberVO.setPassword(newPassword);
				memberDAO.updatePassword(memberVO);
				result = true;
			}
		}
		return result;
	}

}