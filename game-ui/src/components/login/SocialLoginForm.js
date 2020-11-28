import React from "react";
import ApiConstants from "../../constants/ApiConstant";

export default function SocialLoginForm() {
    return (
        <form className="mx-5">
            <h5 className="text-center">Login with</h5>
            <div className="row justify-content-around mt-4">
                <div className="col-6">
                    <div className="form-group">
                        <button type="button" className="form-control btn btn-primary">
                            <i className="fab fa-facebook"/>
                            <span className="pl-2">Facebook</span>
                        </button>
                    </div>
                </div>
                <div className="col-6">
                    <div className="form-group">
                        <a href={ApiConstants.GOOGLE_AUTH_URL} target="_blank" rel="noreferrer"
                           className="form-control btn btn-danger">
                            <i className="fab fa-google-plus-g"/>
                            <span className="pl-2">Google</span>
                        </a>
                    </div>
                </div>
            </div>
            <div className="row justify-content-around">
                <div className="col-6">
                    <div className="form-group">
                        <a href={ApiConstants.GITHUB_AUTH_URL} className="form-control btn btn-secondary">
                            <i className="fab fa-github"/>
                            <span className="pl-2">GitHub</span>
                        </a>
                    </div>
                </div>
                <div className="col-6">
                    <div className="form-group">
                        <button type="button" className="form-control btn btn-dark" data-toggle="modal"
                                data-target="#pornhub-modal">
                            <i className="fas fa-heart"/>
                            <span className="pl-2">PornHub</span>
                        </button>
                        <div className="modal fade" id="pornhub-modal" tabIndex="-1" role="dialog"
                             aria-labelledby="pornhub-title" aria-hidden="true">
                            <div className="modal-dialog" role="document">
                                <div className="modal-content">
                                    <div className="modal-header">
                                        <h5 className="modal-title text-center text-danger" id="pornhub-title">Stop
                                            Stop Stop !!!</h5>
                                        <button type="button" className="close" data-dismiss="modal"
                                                aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div className="modal-body">
                                        <b>Bắt được có tài khoản xem phim heo nha!</b>
                                        <p>Đăng nhập bằng chức năng khác đi!</p>
                                    </div>
                                    <div className="modal-footer">
                                        <button type="button" className="btn btn-secondary"
                                                data-dismiss="modal">Close
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    );
}