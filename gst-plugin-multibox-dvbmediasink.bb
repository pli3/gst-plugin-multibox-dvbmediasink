DESCRIPTION = "gstreamer dvbmediasink plugin"
SECTION = "multimedia"
PRIORITY = "optional"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=278f2557e3b277b94e9a8430f6a6d0a9"

DEPENDS = "gstreamer gst-plugins-base libdca"

SRCREV = "${AUTOREV}"

SRC_URI = "git://github.com/christophecvr/gst-plugin-multibox-dvbmediasink;protocol=https"

S = "${WORKDIR}/git"

inherit gitpkgv

PV = "0.10.0+git${SRCPV}"
PKGV = "0.10.0+git${GITPKGV}"
PR = "r0"

# added to have al m4 macro's into build when using bitbake with -b option.
# Then proceeding to full image build or at least package build with recipes parsing is not needed.
do_configure_prepend() {
	ln -sf ${STAGING_DATADIR_NATIVE}/aclocal/*.m4 ${S}/m4/
}

inherit autotools pkgconfig

FILES_${PN} = "${libdir}/gstreamer-0.10/*.so*"
FILES_${PN}-dev += "${libdir}/gstreamer-0.10/*.la"
FILES_${PN}-staticdev += "${libdir}/gstreamer-0.10/*.a"
FILES_${PN}-dbg += "${libdir}/gstreamer-0.10/.debug"

PACKAGE_ARCH = "${MACHINE_ARCH}"

EXTRA_OECONF = "${DVBMEDIASINK_CONFIG}"


# gstreamer cache files needs to be removed if sink is updated.
pkg_preinst_${PN}_prepend () {
	if [ -d "/.gstreamer-0.10" ]
	then
		rm -rf "/.gstreamer-0.10"
	fi
	if [ -d "/home/root/.gstreamer-0.10" ]
	then
		rm -rf "/home/root/.gstreamer-0.10"
	fi
}
