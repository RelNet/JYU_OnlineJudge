FROM scratch

ADD ../../../oj_ubuntu_16_core_amd64.tar.gz /

RUN set -xe \

    && rm -rf /var/lib/apt/lists/* \

    && echo '#!/bin/sh' > /usr/sbin/policy-rc.d \
    && echo 'exit 101' >> /usr/sbin/policy-rc.d \
    && chmod +x /usr/sbin/policy-rc.d \

    && dpkg-divert --local --rename --add /sbin/initctl \
    && cp -a /usr/sbin/policy-rc.d /sbin/initctl \
   	&& sed -i 's/^exit.*/exit 0/' /sbin/initctl \

   	&& echo 'force-unsafe-io' > /etc/dpkg/dpkg.cfg.d/docker-apt-speedup \

   	&& echo 'DPkg::Post-Invoke { "rm -f /var/cache/apt/archives/*.deb /var/cache/apt/archives/partial/*.deb /var/cache/apt/*.bin || true"; };' > /etc/apt/apt.conf.d/docker-clean \
    && echo 'APT::Update::Post-Invoke { "rm -f /var/cache/apt/archives/*.deb /var/cache/apt/archives/partial/*.deb /var/cache/apt/*.bin || true"; };' >> /etc/apt/apt.conf.d/docker-clean \
   	&& echo 'Dir::Cache::pkgcache ""; Dir::Cache::srcpkgcache "";' >> /etc/apt/apt.conf.d/docker-clean \

    && echo 'Acquire::Languages "none";' > /etc/apt/apt.conf.d/docker-no-languages \

    && echo 'Acquire::GzipIndexes "true"; Acquire::CompressionTypes::Order:: "gz";' > /etc/apt/apt.conf.d/docker-gzip-indexes \

    && echo 'Apt::AutoRemove::SuggestsImportant "false";' > /etc/apt/apt.conf.d/docker-autoremove-suggests \

    && mkdir -p /run/systemd && echo 'docker' > /run/systemd/container \

    && mkdir -p /run/systemd && echo 'docker' > /run/systemd/container \

    # 安装gcc
    && sudo apt-get update \
    && sudo apt-get install g++ \
    && sudo apt-get clean \
    && sudo apt-get -y autoremove \
    && sudo rm -rf /var/lib/apt/lists/* \

ENTRYPOINT ["/bin/bash", "g++ -o"]