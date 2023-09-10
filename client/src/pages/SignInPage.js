import { Grid, TextField, Button } from "@mui/material";
import React, { useState } from "react";
import UpperNavigationBar from "../components/UpperNavigationBar";
import './SignInPage.css';
import { Link } from "react-router-dom";
import axios from 'axios';

const SignInPage = () => {
  const upperNavbarName = "로그인";
  const [formData, setFormData] = useState({
    loginId: "",
    loginPwd: "",
  });

  // accessToken 상태 추가
  const [accessToken, setAccessToken] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('/api/auth/login', formData);

      console.log(response.data);
      console.log(response.data.dataBody.accessToken);

      // accessToken을 localStorage에 저장
      localStorage.setItem('accessToken', response.data.dataBody.accessToken);

      // accessToken 상태 업데이트
      setAccessToken(response.data.dataBody.accessToken);

      setFormData({
        loginId: "",
        loginPwd: "",
      });
    } catch (error) {
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
