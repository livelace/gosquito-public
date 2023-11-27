libraries {
    git {
        repo_url = "git@github.com:livelace/gosquito-public.git"
    }
    k8s_app {
        cluster = "k8s-4"
        namespace = "gosquito"
        image = "harbor-core.k8s-2.livelace.ru/portage/gosquito-update:1.0.0"
        volume = """
            gosquito-public-conf, data/gosquito/conf, rw
        """
    }
}

