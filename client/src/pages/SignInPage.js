import { Grid, TextField, Button } from "@mui/material";
import React, { useState } from "react";
import UpperNavigationBar from "../components/UpperNavigationBar";
import './SignInPage.css';
import { Link } from "react-router-dom";
import axios from 'axios'; // Axios를 import합니다.

const SignInPage = () => {
  const upperNavbarName = "로그인";
  const [formData, setFormData] = useState({
    loginId: "",
    loginPwd: "",
  });

  // 폼 제출을 처리할 handleSubmit 함수를 정의합니다.
  const handleSubmit = async (e) => {
    e.preventDefault(); // 기본 폼 제출 동작을 막습니다.
    try {
      // 폼 데이터와 함께 API 엔드포인트로 POST 요청을 보냅니다.
      const response = await axios.post('/api/auth/login', formData);

      // 응답을 처리합니다 (예: 성공 메시지 표시 또는 사용자를 리디렉션합니다).
      console.log(response.data); // API 응답에 따라 커스터마이즈할 수 있습니다.

      // 성공적인 제출 이후에 폼을 재설정합니다.
      setFormData({
        loginId: "",
        loginPwd: "",
      });
    } catch (error) {
      // 에러를 처리합니다 (예: 에러 메시지를 표시합니다).
      console.error(error);
    }
  };

  return (
    <Grid container spacing={2} justifyContent="center" alignItems="center">
      <Grid item xs={12}>
        <UpperNavigationBar props={upperNavbarName} />
      </Grid>
      <Grid className="progressive_bar"></Grid>
      <Grid item xs={12}>
        <form onSubmit={handleSubmit}>
          <Grid container spacing={2} justifyContent="center" alignItems="center">
            <Grid item xs={12}>
              <TextField
                type="text"
                label="아이디"
                variant="outlined"
                fullWidth
                value={formData.loginId}
                onChange={(e) => setFormData({ ...formData, loginId: e.target.value })}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                type="password"
                label="비밀번호"
                variant="outlined"
                fullWidth
                value={formData.loginPwd}
                onChange={(e) => setFormData({ ...formData, loginPwd: e.target.value })}
              />
            </Grid>
            <Grid item xs={12}>
              <Button
                type="submit"
                variant="contained"
                color="primary"
                fullWidth
              >
                로그인
              </Button>
            </Grid>
            <Grid item xs={12}>
              <Button variant="outlined" color="primary" fullWidth>
                <Link to="/signup">
                  회원가입
                </Link>
              </Button>
            </Grid>
            <Grid item xs={6}>
              <Button variant="text" color="primary" fullWidth>
                아이디 찾기
              </Button>
            </Grid>
            <Grid item xs={6}>
              <Button variant="text" color="primary" fullWidth>
                비밀번호 찾기
              </Button>
            </Grid>
          </Grid>
        </form>
      </Grid>
    </Grid>
  );
};

export default SignInPage;
