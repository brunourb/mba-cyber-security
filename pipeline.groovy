#!/usr/bin/env groovy

pipeline {
    //Agent é o NÓ que vai rodar o job
    agent any

    //Fases do pipeline
    stages {
        
       stage('build') {
            steps {
                script {
                    echo 'fazendo o build do projeto'
                    git branch: 'master', url: 'https://github.com/paulonill/exemplo-spring-mvc-thymeleaf.git'
                    sh "ls -la"
                }
            }
        }
        
       stage('unit-test') {
            steps {
                script {
                    echo 'fazendo o test do projeto'
                }
            }
        }
        
       stage('deploy to stage') {
            steps {
                script {
                    echo 'fazendo o deploy to stage do projeto'
                }
            }
        }
        
       stage('acceptance test') {
            steps {
                script {
                    echo 'fazendo o acceptance test do projeto'
                }
            }
        }
        
        stage('Deploy em Produção') {
            steps {
                script {
                    def deploymentDelay = input id: 'Deploy', message: 'Deploy to production?', submitter: 'rkivisto,admin', parameters: [choice(choices: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24'], description: 'Hours to delay deployment?', name: 'deploymentDelay')]
                    sleep time: deploymentDelay.toInteger(), unit: 'HOURS'
                    echo 'Deploy em produção'
                    
                    def discordImageSuccess = 'https://www.jenkins.io/images/logos/formal/256.png'
                    def discordImageError = 'https://www.jenkins.io/images/logos/fire/256.png'

                    def discordDesc =
                            "Result: ${currentBuild.currentResult}\n" +
                                    "Project: Nome projeto\n" +
                                    "Commit: Quem fez commit\n" +
                                    "Author: Autor do commit\n" +
                                    "Message: mensagem do changelog ou commit\n" +
                                    "Duration: ${currentBuild.durationString}"

                                    //Variaveis de ambiente do Jenkins - NOME DO JOB E NÚMERO DO JOB
                                    def discordFooter = "${env.JOB_BASE_NAME} (#${BUILD_NUMBER})"
                                    def discordTitle = "${env.JOB_BASE_NAME} (build #${BUILD_NUMBER})"
                                    def urlWebhook = "https://discord.com/api/webhooks/883733040646483978/1ww2MvJ4oHCKglPFAia1eFpB_2aNpSfjtZS-FOJTsLtDdY0lQFM2Zw_vLLTaDMT2SKLc"
                                    //def urlWebhook = "https://discord.com/api/webhooks/711712945934958603/tZiZgmNgW_lHleONDiPu5RVM24wbuxFKcpMBDJsY2WxSqjltAz3UCYupqSIE7q0rlmHP"

                    discordSend description: discordDesc,
                            footer: discordFooter,
                            link: env.JOB_URL,
                            result: currentBuild.currentResult,
                            title: discordTitle,
                            webhookURL: urlWebhook,
                            successful: currentBuild.resultIsBetterOrEqualTo('SUCCESS'),
                            thumbnail: 'SUCCESS'.equals(currentBuild.currentResult) ? discordImageSuccess : discordImageError
                    
                    
                    
                }
            }
        }
     }
}
