import { Grid, TextField, Button } from "@mui/material";
import React from "react";
import UpperNavigationBar from "../components/UpperNavigationBar";
import './SignInPage.css';

const SignInPage = () => {
  const upperNavbarName = "로그인";

  return (
    <Grid container spacing={2} justifyContent="center" alignItems="center">
      <Grid item xs={12}>
        <UpperNavigationBar props={upperNavbarName} />
      </Grid>
      <Grid className="progressive_bar"></Grid>
      <Grid item xs={12}>
        <form>
          <Grid container spacing={2} justifyContent="center" alignItems="center">
            <Grid item xs={12}>
              <TextField
                type="text"
                label="아이디"
                variant="outlined"
                fullWidth
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                type="password"
                label="비밀번호"
                variant="outlined"
                fullWidth
              />
            </Grid>
            <Grid item xs={12}>
              <Button
                variant="contained"
                color="primary"
                fullWidth
              >
                로그인
              </Button>
            </Grid>
            <Grid item xs={12}>
              <Button
                variant="outlined"
                color="primary"
                fullWidth
              >
                회원가입
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
