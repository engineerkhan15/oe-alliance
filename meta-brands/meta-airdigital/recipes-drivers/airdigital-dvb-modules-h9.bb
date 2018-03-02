KV = "4.4.35"
SRCDATE = "20180228"

RDEPENDS_${PN} = "libjpeg-turbo pulseaudio-lib-rtp"

PROVIDES += " virtual/blindscan-dvbc virtual/blindscan-dvbs"

require airdigital-dvb-modules.inc

SRC_URI[md5sum] = "1cb461e610b29927225a925941b86581"
SRC_URI[sha256sum] = "f184b030b82cd66531c296bf473f64762b946eb124ec749cb5b1623e3bac823b"

INITSCRIPT_NAME = "suspend"
INITSCRIPT_PARAMS = "start 89 0 ."
inherit update-rc.d

do_install_append() {
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${bindir}
	echo "#!/bin/sh" > ${S}/suspend
	echo "mount -t sysfs sys /sys" >> ${S}/suspend
	echo "/usr/bin/turnoff_power" >> ${S}/suspend
	install -m 0755 ${S}/suspend ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/turnoff_power ${D}${bindir}
}

pkg_prerm_${PN}() {
	if [ "x$D" == "x" ]; then
		if [ -f /lib/modules/${KV}/extra/hi_play.ko ] ; then
			rm -f /lib/modules/${KV}/extra/hi_play.ko;
		fi
	fi
}

do_package_qa() {
}

FILES_${PN} += " ${bindir} ${sysconfdir}/init.d"

INSANE_SKIP_${PN} += "already-stripped ldflags"