import React from "react";
import {Form} from "formik";
import {GoogleLogin} from 'react-google-login';

const clientId = "643460842111-r1b9f3fjk6cenot7n9u76g6sb2vavvdr.apps.googleusercontent.com"

const onSuccess = (res) => {
  console.log("LOGIN SUCCESS! Current user: ", res.profileObj);
}

const onFailure = (res) => {
  console.log("LOGIN FAILED! res: ", res);
}


export class Login extends React.Component {

  render() {
    return (
      <div className="base-container" ref={this.props.containerRef}>

        <div className="content">
          <div className="form">
            <div className="form-group">
              <div className="form-group">
                <label htmlFor="username">Username</label>
                <input
                  type="text"
                  name="username"
                  placeholder="username"
                  value={this.props.username}
                  onChange={this.props.handleChange}
                ></input>
              </div>
              <div className="form-group">
                <label htmlFor="password">Password</label>
                <input
                  type="password"
                  name="password"
                  placeholder="password"
                  value={this.props.password}
                  onChange={this.props.handleChange}
                ></input>
              </div>
            </div>
          </div>
          <div className={"footerBtn"}>
          <button
              type="button"
              className="btn btn-success"
              name={"login"}
              onClick={this.props.handleSubmit}
            >
              Login
            </button>
            <button
                type="button" name={"register"}
                className="btn btn-info"
                onClick={this.props.changeState}
            >
              register
            </button>
          </div>

          <div className="google-login-container">
            <GoogleLogin
              clientId={clientId}
              buttonText="Login With Google"
              onSuccess={onSuccess}
              onFailure={onFailure}
              cookiePolicy={'single_host_origin'}
              isSignedIn={true}
            />

          <a href="/oauth2/authorization/google">Login with Google</a>

          </div>
          
        </div>
      </div>
    );
  }
}
