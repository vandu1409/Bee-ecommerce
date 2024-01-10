import React, { useState } from "react";
import { FaPen } from "react-icons/fa6";
import { FaRegUser } from "react-icons/fa6";
import { BiNotepad } from "react-icons/bi";
import "./Profile.css";
import { Link, useLocation } from "react-router-dom";
import Header from "~/components/layout/customer/Header/Header";
import Footer from "~/components/layout/customer/Footer/Footer";
import * as PropTypes from "prop-types";
import ProfileSideBar from "~/components/layout/customer/ProfileSideBar";

const ProfileLayout = ({ children }) => {

    return (
        <>
            <Header/>
            <ProfileSideBar>
                {children}
            </ProfileSideBar>
            <Footer/>
        </>
    );
};

export default ProfileLayout;
